# AmazingMovies

En esta aplicación puedes visualizar tanto online como offline:
Las peliculas mas populares, las mejor clasificadas y las proximas por estrenarse.
Se puede ver un trailer de cada pelicula asi como el detalle de esta.
Puedes realizar busquedas de peliculas por genero.

1. Las capas de la aplicación (por ejemplo capa de persistencia, vistas, red, negocio, etc) y qué clases pertenecen a cual.

- Persistencia.

La capa de persistencia almacena los datos que se consideran importantes o simplemente para cumplir una funcion en específico,
en está aplicación para esta capa se implemento Realm para guardar el listado de todas las peliculas de manera local y asi poder consultarlas sin conexión.
core.broadcasts.ConnectionReceiver, core.repository.models.MovieInfo, core.repository.models.GetMoviesResponse.

- Vistas.

La arquitectura de esta aplicación es MVVM, por lo que el viewmodel contiene toda la lógica de presentación y el view contiene los datos y la forma en la que se va a mostrar,
este se encuentra dentro del viewmodel de cada modulo.
HomeViewModel, SearchViewModel.

- Red.

La capa de red define la comunicación al API de the movie db, dentro del package core.repository.Api y core.repository.ApiGenre está la configuración de Retrofit y el
mapeo de los servicios que se requieren.


- Negocio.

La lógica del negocio se encuentra definida en el repository de cada modulo.
HomeRepository, HomeOfflineRepository, SearchRepository.

2. La responsabilidad de cada clase creada.

CoreApplication: Define la inyección de dependecias de la aplicación y la configuración de Realm.

ConnectionReceiver: Obtiene el estado de la conexión a internet.

ViewModelFactory: Especifíca la forma de crear un viewmodel.

ViewModelKey: Permite a dagger crear una instancia de un viewmodel a traves de un key.

ActivitiesModule: Se difinen las actividades de la aplicación.

BroadcastReceiverModule: Se definen los BroadcastReceiver a utilizar.

ContextModule: Se define el contexto de la aplicación.

FeaturesModule: Se definen los MutableLiveData.

FragmentModule: Se definen los fragmentos que componen la aplicación.

NetworkModule: Se definen las apis que se van a consumir y lo repositorios implementados.

ViewModelModule: Se definen los viewmodel empleados en la aplicación.

AppComponent: Se definen los modulos que se emplean, las apis, los livedata.

RealmMigrationConfig: Se utiliza para realizar la migración de un esquema de Realm a otro.

Genre, GenreResponse, GetMoviesResponse, MovieInfo: Modelos de las respuestas de los servicios consumidos.

Api, ApiGenre: Mapeo de los servicios utilizados.

ImageUrl: Genera una imagen de un URL.

ActivityInteraction: Define las acciones que se pueden realizar en la actividad.

BaseActivity: Define la configuración de la actividad y las acciones que se pueden realizar sobre ella.

Initializer: Define funciones a implementar por los fragmentos o actividades.

MainActivity: Actividad principal.

DashboardActivity: Actividad que contiene el menu de navegacion asi como los demas fragmentos.

MovieDetail: Modelo que contiene los datos a mostrar en el fragmento de MovieDetail.

MovieDetailFragment: La vista del detalle de una pelicula.

PopularMovieAdapter: ViewHolder para mostrar el listado de las peliculas mas populares en el recyclerview.

TopMovieAdapter: ViewHolder para mostrar el listado de las peliculas mejor clasificadas en el recyclerview.

UpcomingMovieAdapter: ViewHolder para mostrar el listado de las peliculas proximas en el recyclerview.

HomeFragment: La vista de home que muestra el listado de las categorias y las peliculas que pertenecen a cada una.

HomeOfflineRepository: Consume los datos guardados en realm de las peliculas para poder mostrar las peliculas que pertenecen 
a cada categoria cuando no hay conexion.

HomeRepository: Consume el api de themoviedb para obtener el listado de las peliculas por categoria.

HomeViewModel: Contiene la logica de presentación de los datos del home.

SearchAdapter: ViewHolder para mostrar el listado de las peliculas por genero.

MovieFind: Modelo que contiene los datos a mostrar en el fragmento de Search.

SearchFragment: La vista para buscar peliculas por genero.

SearchRepository: Consume el api de themoviedb para obtener el listado de las peliculas por genero.

SearchViewModel: Contiene la logica de presentación de los datos de search.

SplashFragment: Contiene la vista del splash de la aplicación.

3. En qué consiste el principio de responsabilidad única? Cuál es su propósito?

El principio de responsabilidad única consiste en separar los comportamientos y asi establecer responsabilidades en concreto
para cada clase, su propósito es establecer funcionabilidades mas concretas y mas específicas.

4. Qué características tiene, según su opinión, un “buen” código o código limpio?

Considero que un buen código o código limpio debe de poder ser legible para otro programador, deben estar bien definidas las funcionalidades
empezando desde el nombre de las variables, métodos o clases, debe estar identado y utilizar patrones de diseño para resolver problemas comunes y
el desarrollo debe estar guiado por un arquitectura.

Raquel Ariadna Martínez Bautista.
Android Developer.
