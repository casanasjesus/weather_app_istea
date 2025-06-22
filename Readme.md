# 🌦 Weather Application - Kotlin (ISTEA)

Aplicación móvil desarrollada en **Kotlin** como parte de la materia Aplicaciones Móviles en ISTEA. La app permite consultar el estado del clima en ciudades específicas, ya sea buscándolas manualmente o utilizando la ubicación actual del dispositivo. Implementa almacenamiento local para mantener el estado al reiniciar la aplicación.

## 🛠 Arquitectura y Estructura

El proyecto sigue una estructura modular dentro del entorno Android:

- `MainActivity.kt`: Entrypoint de la aplicación.
- `ciudades`: Permite buscar ciudades y utilizar la geolocalización.
- `clima`: Muestra el clima actual de la ciudad seleccionada.
- `models`: Registra el formato de los objetos Ciudad y Clima.
- `repository`: Almacena los servicios para buscar ciudad o ciudades y obtener el clima de la ciudad seleccionada.
- `storage`: Almacena la persistencia local con Context DataStore.
- `ui.theme`: Almacena la configuración de tipografía y colores de la aplicación.


## 📱 Comportamiento de la app

- 🔍 **Búsqueda de ciudades:** Ingresá el nombre de una ciudad para obtener información del clima.
- 📍 **Ubicación actual:** Obtené el clima automáticamente según tu ubicación.
- 🕒 **Persistencia de ciudad:** La última ciudad consultada se guarda en el almacenamiento local para cargar automáticamente al reiniciar la aplicación.
- 🧭 **Navegación automática:**
    - Si **ya hay una ciudad guardada**, la app inicia directamente en la pantalla del clima de esa ciudad.
    - Si **no hay ninguna ciudad guardada**, se muestra la pantalla para buscar ciudades o usar la ubicación actual.


## 🛠 Tecnologías y herramientas

- **Lenguaje:** Kotlin
- **Plataforma:** Android SDK
- **Persistencia:** Context DataStore
- **API Open Weather:** https://openweathermap.org/
- **IDE:** Android Studio
- **Gradle:** Kotlin DSL (`*.kts`)


## 🚀 Cómo ejecutar

1. Cloná el repositorio:
   ```bash
   git clone https://github.com/casanasjesus/weather_app_iste.git
   ```
   
2. Abrí el proyecto en Android Studio

3. Ejecutar la app en un emulador o dispositivo físico

## 📌 Notas adicionales

1. Asegurate de tener habilitados los permisos de ubicación para un correcto funcionamiento.

2. Si querés resetear la ciudad guardada, podés limpiar los datos de la app desde el sistema.