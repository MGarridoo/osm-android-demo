package com.example.osmdemo.features.trip_search.data

import android.util.Log
import com.example.osmdemo.core.backend.domain.BackendRepository
import com.example.osmdemo.core.backend.domain.NominatimRepository
import com.example.osmdemo.core.backend.model.StopOrCoordLocation
import com.example.osmdemo.core.backend.utils.BackendResult
import com.example.osmdemo.features.trip_search.domain.TripSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class TripSearchRepositoryImpl @Inject constructor(
    private val repo: BackendRepository,
    private val nominatimAPI: NominatimRepository,
) : TripSearchRepository {

    override fun locationName(location: String): Flow<BackendResult<List<StopOrCoordLocation>>> {
        // Flujo 1: Consulta a la API principal
        val main = repo.retrieveLocationName(input = location)

        // Flujo 2: Consulta a la API de Nominatim
        val nominatim = nominatimAPI.search(query = location)

        return combine(main, nominatim) { result, result2 ->
            when {
                result is BackendResult.Success && result2 is BackendResult.Success -> {

                    // Transforma cada OSMPlace en StopOrCoordLocation usando la propiedad calculada
                    val nominatimCoordLocations = result2.data.map { it.toStopOrCoordLocation }

                    // Combinas ambas listas en una sola
                    val allLocations = result.data + nominatimCoordLocations

                    // Devuelves el resultado final unificado dentro de un LocationResponse
                    BackendResult.Success(allLocations)
                }

                result is BackendResult.Error -> {
                    Log.d("TAG", "locationName: result ${result.message}")
                    BackendResult.Error(result.code, result.message)
                }

                result2 is BackendResult.Error -> {
                    Log.d("TAG", "locationName: result2 ${result2.message}")
                    BackendResult.Error(result2.code, result2.message)
                }

                else -> {
                    Log.d("TAG", "else locationName: $result")
                    Log.d("TAG", "else locationName: $result2")
                    BackendResult.Exception(Exception("Error combinando resultados de APIs"))
                }

            }
        }.catch { e ->
            Log.d("TAG", "locationName: $e")
            emit(BackendResult.Exception(e))
        }
    }
}