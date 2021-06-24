package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.data.entity.WeatherForLocation
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.home.HomeState
import br.com.renatoarg.weatherapp2021.home.HomeViewModel
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.orbitmvi.orbit.assert
import org.orbitmvi.orbit.test

@ExperimentalUnsignedTypes
@ExperimentalCoroutinesApi
class ExampleUnitTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var repository: HomeRepository

    @Test
    fun `test1`() {
        val weatherForLocation = Gson().fromJson(mockData, WeatherForLocation::class.java)

        val viewModel =
            HomeViewModel(repository).test(HomeState())

        val response =
            MockAPIResponse.getResponse<WeatherForLocation>(true, data = weatherForLocation)

        mainCoroutineRule.runBlockingTest {
            whenever(repository.fetchWeatherForLocation(1)).thenReturn(response)
        }

        viewModel.fetchWeatherForLocation(1)
        viewModel.assert(HomeState()) {
            states(
                { copy(isLoading = true) },
                { copy(weatherForLocation = weatherForLocation) }
            )
        }
    }
}

val mockData: String = """
        {
           "consolidated_weather":[
              {
                 "id":1,
                 "weather_state_name":"Renato Heavy Cloud",
                 "weather_state_abbr":"hc",
                 "wind_direction_compass":"SW",
                 "created":"2021-06-22T18:32:16.777298Z",
                 "applicable_date":"2021-06-22",
                 "min_temp":14.275,
                 "max_temp":17.865000000000002,
                 "the_temp":18.424999999999997,
                 "wind_speed":6.845822937925182,
                 "wind_direction":226.3097152649591,
                 "air_pressure":1013.5,
                 "humidity":74,
                 "visibility":13.64375795355126,
                 "predictability":71
              },
              {
                 "id":2,
                 "weather_state_name":"Light Cloud",
                 "weather_state_abbr":"lc",
                 "wind_direction_compass":"WSW",
                 "created":"2021-06-22T18:32:19.683951Z",
                 "applicable_date":"2021-06-23",
                 "min_temp":13.295000000000002,
                 "max_temp":18.564999999999998,
                 "the_temp":18.805,
                 "wind_speed":6.12151867473952,
                 "wind_direction":242.36844927309647,
                 "air_pressure":1014.5,
                 "humidity":69,
                 "visibility":12.26213910761155,
                 "predictability":70
              },
              {
                 "id":3,
                 "weather_state_name":"Light Cloud",
                 "weather_state_abbr":"lc",
                 "wind_direction_compass":"WSW",
                 "created":"2021-06-22T18:32:24.155582Z",
                 "applicable_date":"2021-06-24",
                 "min_temp":12.379999999999999,
                 "max_temp":17.810000000000002,
                 "the_temp":18.745,
                 "wind_speed":6.570601148707548,
                 "wind_direction":253.83140625998118,
                 "air_pressure":1017.0,
                 "humidity":67,
                 "visibility":14.245555953233119,
                 "predictability":70
              },
              {
                 "id":4,
                 "weather_state_name":"Light Cloud",
                 "weather_state_abbr":"lc",
                 "wind_direction_compass":"WSW",
                 "created":"2021-06-22T18:32:25.688778Z",
                 "applicable_date":"2021-06-25",
                 "min_temp":12.325,
                 "max_temp":17.445,
                 "the_temp":18.75,
                 "wind_speed":6.999141238325511,
                 "wind_direction":249.8330205318604,
                 "air_pressure":1015.0,
                 "humidity":69,
                 "visibility":13.749391056231607,
                 "predictability":70
              },
              {
                 "id":5,
                 "weather_state_name":"Heavy Cloud",
                 "weather_state_abbr":"hc",
                 "wind_direction_compass":"W",
                 "created":"2021-06-22T18:32:29.249333Z",
                 "applicable_date":"2021-06-26",
                 "min_temp":12.620000000000001,
                 "max_temp":18.485,
                 "the_temp":21.16,
                 "wind_speed":6.815354635645165,
                 "wind_direction":260.6562956988091,
                 "air_pressure":1012.5,
                 "humidity":65,
                 "visibility":13.131748091147696,
                 "predictability":71
              },
              {
                 "id":6045003915722752,
                 "weather_state_name":"Heavy Cloud",
                 "weather_state_abbr":"hc",
                 "wind_direction_compass":"WSW",
                 "created":"2021-06-22T18:32:31.868621Z",
                 "applicable_date":"2021-06-27",
                 "min_temp":12.940000000000001,
                 "max_temp":18.745,
                 "the_temp":19.07,
                 "wind_speed":6.953341485723374,
                 "wind_direction":254.0,
                 "air_pressure":1007.0,
                 "humidity":71,
                 "visibility":9.318703769983298,
                 "predictability":71
              }
           ],
           "time":"2021-06-22T14:21:54.699884-07:00",
           "sun_rise":"2021-06-22T05:48:21.903536-07:00",
           "sun_set":"2021-06-22T20:35:05.465281-07:00",
           "timezone_name":"LMT",
           "parent":{
              "title":"California",
              "location_type":"Region / State / Province",
              "woeid":2347563,
              "latt_long":"37.271881,-119.270233"
           },
           "sources":[
              {
                 "title":"BBC",
                 "slug":"bbc",
                 "url":"http://www.bbc.co.uk/weather/",
                 "crawl_rate":360
              },
              {
                 "title":"Forecast.io",
                 "slug":"forecast-io",
                 "url":"http://forecast.io/",
                 "crawl_rate":480
              },
              {
                 "title":"HAMweather",
                 "slug":"hamweather",
                 "url":"http://www.hamweather.com/",
                 "crawl_rate":360
              },
              {
                 "title":"Met Office",
                 "slug":"met-office",
                 "url":"http://www.metoffice.gov.uk/",
                 "crawl_rate":180
              },
              {
                 "title":"OpenWeatherMap",
                 "slug":"openweathermap",
                 "url":"http://openweathermap.org/",
                 "crawl_rate":360
              },
              {
                 "title":"Weather Underground",
                 "slug":"wunderground",
                 "url":"https://www.wunderground.com/?apiref=fc30dc3cd224e19b",
                 "crawl_rate":720
              },
              {
                 "title":"World Weather Online",
                 "slug":"world-weather-online",
                 "url":"http://www.worldweatheronline.com/",
                 "crawl_rate":360
              }
           ],
           "title":"San Francisco",
           "location_type":"City",
           "woeid":2487956,
           "latt_long":"37.777119, -122.41964",
           "timezone":"US/Pacific"
        }
    """.trimIndent()