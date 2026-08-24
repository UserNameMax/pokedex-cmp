# Pokédex — каркас на Compose Multiplatform

Шаблон к воркшопу **ПЗ4 «Данные на CMP»**, 31.10, очно.
Курс кроссплатформенной разработки, группа ПИН-252т.

Продолжение ПЗ1 (`workshop/w1-end`): каркас на моках заменяется живыми данными.

## До пары

Обязательно, иначе потеряете полпары на Gradle:

```bash
git clone https://github.com/UserNameMax/pokedex-cmp.git && cd pokedex-cmp
git switch -c feature/lab-4_data w4-start
./gradlew :desktopApp:run
```

Вторая строка обязательна. Без неё `git checkout w4-start` оставляет вас в
detached HEAD, и первый же коммит окажется ни в одной ветке — Git о нём
предупредит одной строкой, которую на паре никто не прочитает.

Открылось окно со списком на моках — вы готовы. Проверка идёт в первые восемь минут занятия.

## Запуск

```bash
./gradlew :desktopApp:run                        # основной таргет воркшопа
./gradlew :webApp:wasmJsBrowserDevelopmentRun    # понадобится на чекпойнте про уход с экрана
./gradlew :androidApp:assembleDebug              # у кого настроен SDK
```

Воркшоп ведётся на **desktop**. Web поднимается один раз — посмотреть то же самое в браузере.
Android — в конце, по желанию.

## Чекпойнты и догон

Каждый чекпойнт помечен тегом. Отстали — не догоняйте вручную, возьмите ветку от нужного тега:

```bash
git add -A && git commit -m wip
git switch -c catchup-cp3 w4-cp3
```

Своя работа при этом остаётся в вашей ветке, вернуться к ней —
`git switch feature/lab-4_data`.

Теги: `w4-start` → `w4-cp1` … `w4-cp5` → `w4-end`. Каждый собирается и запускается.

## Что должно быть на выходе

TBD — заполняется по факту прохода воркшопа.

## Что уже лежит в проекте

Всё, что не тема пары, дано целиком: сеть, хранилище, картинки и скелет слоёв.
Пишется на паре то, ради чего пара существует, — по чекпойнтам ниже.

| Путь | Что это |
|---|---|
| `data/network/PokeApiClient.kt` | Ktor-клиент: базовый адрес, JSON, лог запросов в консоль |
| `data/network/PokeApi.kt` | четыре запроса к PokéAPI: список, деталь, вид (species), цепочка эволюции — каждый отдаёт `Result` |
| `data/network/dto/`, `data/network/PokemonDetailMapper.kt` | формы ответов PokéAPI — по файлу на ответ — и склейка трёх ответов в одну доменную запись (`createPokemon`) |
| `data/network/SpriteUrl.kt` | адрес спрайта по номеру записи — без отдельного запроса |
| `core/SuspendRunCatching.kt` | `runCatching`, который не глотает отмену корутины |
| `domain/model/`, `domain/repository/PokemonRepository.kt` | доменные модели (`Pokemon`, `PokemonType`, `LoadStatus`) и чтение каталога: страница записями по одной (`Flow`) и запись по id |
| `domain/repository/PokemonLocalRepository.kt`, `data/InMemoryPokemonRepository.kt` | локальное хранилище: то же чтение плюс запись; запись с тем же id заменяет старую |
| `data/MockPokemonRepository.kt`, `data/MockPokemons.kt` | каталог на моках из ПЗ1 — на чекпойнте 1 его сменит сеть |
| `domain/LoadPokemonUseCase.kt` | страницы каталога — пока только первая |
| `domain/GetPokemonInteractor.kt` | дирижёр: страницы кладёт в хранилище, экрану отдаёт список и статус загрузки (`LoadStatus`) |
| `ui/model/PokemonImageModel.kt`, `ui/components/Image.kt` | картинка из ресурсов или по URL — одним компонентом |
| `ui/components/FullScreenLoading.kt`, `FullScreenError.kt` | загрузка и ошибка на весь экран — понадобятся на чекпойнтах 3 и 4 |
| `ui/screens/*/model`, `ui/screens/*/view_model` | состояние и намерения экрана, его ViewModel и фабрика — по подпакетам |
| `ui/navigation/` | Navigator (бэкстек) и AppNavDisplay (NavDisplay и записи экранов) из ПЗ1 |

Всё остальное из ПЗ1 (карточка, деталь, тема, локали, поиск) остаётся как есть — сеть
встаёт поверх, слои не трогаются.

## Правила, по которым принимается работа

**Строк в коде нет.** Любая подпись, которую видит пользователь, лежит в
`composeResources`. Проверяется скриптом:

```bash
python3 tools/check-strings.py
```

**Секретов в git нет.** PokéAPI ключа не требует; если у вас свой источник данных для
лабораторной — ключ в `local.properties`, не в коде.

**Модель ответа отделена от модели экрана.** DTO → domain → то, что рисует экран — три
разных типа. Переименовали поле на сервере — правится один файл.
