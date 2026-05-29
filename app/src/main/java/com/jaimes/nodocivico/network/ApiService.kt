package com.jaimes.nodocivico.network

import retrofit2.Response
import retrofit2.http.*

data class ReporteApi(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val prioridad: String,
    val ubicacion: String,
    val fecha: String,
    val estado: String = "Pendiente"
)

data class CategoriaApi(
    val id: Int,
    val nombre: String
)

data class UsuarioApi(
    val id: Int = 0,
    val nombre: String,
    val email: String
)

interface ApiService {

    @GET("reportes")
    suspend fun listarReportes(): Response<List<ReporteApi>>

    @POST("reportes")
    suspend fun crearReporte(@Body reporte: ReporteApi): Response<ReporteApi>

    @GET("reportes/{id}")
    suspend fun obtenerReporte(@Path("id") id: Int): Response<ReporteApi>

    @PUT("reportes/{id}")
    suspend fun actualizarEstado(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): Response<ReporteApi>

    @GET("categorias")
    suspend fun listarCategorias(): Response<List<CategoriaApi>>

    @POST("usuarios")
    suspend fun registrarUsuario(@Body usuario: UsuarioApi): Response<UsuarioApi>
}