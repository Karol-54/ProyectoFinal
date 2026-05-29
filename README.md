# Nodo Cívico - Aplicaciónes moviles

Aplicación móvil para reportes vecinales, seguimiento comunitario y acciones coordinadas de microgestión urbana.

## Repositorios
- **App Android:** https://github.com/Karol-54/ProyectoFinal
- **API Flask:** https://github.com/KEVIN4570/NodoCivicoAPI

## Requisitos
- Android Studio
- Python 3.x
- pip

## Cómo correr la API

1. Clona el repositorio de la API:
   `git clone https://github.com/KEVIN4570/NodoCivicoAPI.git`
   `cd NodoCivicoAPI`

2. Instala Flask:
   `pip install flask`

3. Corre la API:
   `python app.py`

4. La API quedará corriendo en:
   `http://127.0.0.1:5000`
   `http://TU_IP_LOCAL:5000`

## Cómo correr la app Android

1. Clona el repositorio:
   `git clone https://github.com/Karol-54/ProyectoFinal.git`

2. Abre el proyecto en Android Studio

3. Asegúrate de que la API esté corriendo en tu PC

4. Corre la app en un emulador — la IP `10.0.2.2:5000` apunta automáticamente a tu localhost

## Endpoints de la API

| Método | Ruta          | Descripción |
|--------|---------------|-------------|
| GET | /reportes     | Listar reportes |
| POST | /reportes     | Crear reporte |
| GET | /reportes/{id} | Obtener reporte |
| PUT | /reportes/{id} | Actualizar estado |
| GET | /categorias   | Listar categorías |
| POST | /usuarios     | Registrar usuario |

## Tecnologías utilizadas

- **Android:** Kotlin, Room, Retrofit, Navigation Component, ViewModel, LiveData
- **Backend:** Python, Flask
- **Arquitectura:** MVVM, Repository Pattern, offline-first