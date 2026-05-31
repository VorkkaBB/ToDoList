# 📝 Lista de Tareas (To-Do List) - Android Moderno

Esta aplicación es una lista de tareas pendientes desarrollada nativamente para Android. El proyecto fue construido siguiendo las mejores prácticas de la arquitectura moderna recomendada por Google, garantizando un flujo de datos unidireccional y reactivo.

## ✨ Características Principales

* **Persistencia Local:** Las tareas se guardan de forma segura en el dispositivo y sobreviven aunque la aplicación se cierre o el dispositivo se reinicie.
* **Reactividad en Tiempo Real:** La interfaz se actualiza automáticamente al agregar o eliminar una tarea sin necesidad de recargar la pantalla manualmente.
* **Gestión Segura de Errores:** Incluye un cuadro de diálogo (`AlertDialog`) de confirmación al mantener presionada una tarea (Long Press) para evitar borrados accidentales.
* **Diseño Adaptativo (Material 3):** La interfaz visual se adapta automáticamente al Modo Claro u Oscuro según la configuración del sistema del usuario.

## 🛠️ Arquitectura y Tecnologías Utilizadas

La aplicación implementa el patrón de diseño **MVVM (Model-View-ViewModel)** estructurado en las siguientes capas lógicas:

1. **Room Database (Capa de Datos):** * Se utilizó `Room` como capa de abstracción sobre SQLite. 
   * La entidad `Task` define la tabla, mientras que `TaskDao` gestiona las operaciones de escritura/lectura (`@Insert`, `@Delete`, `@Query`).

2. **Kotlin Flow (Canal de Comunicación):**
   * El DAO devuelve las tareas como un `Flow<List<Task>>`. Esto crea una "tubería" reactiva donde la base de datos notifica automáticamente al `ViewModel` cualquier cambio en la tabla, eliminando la necesidad de realizar consultas manuales repetitivas.

3. **ViewModel (Gestor de Estado):**
   * `TaskViewModel` se encarga de aislar la lógica de negocio de la interfaz visual. 
   * Convierte el `Flow` de Room en un `StateFlow` que sobrevive a los cambios de configuración (como la rotación de pantalla). Además, ejecuta las operaciones de base de datos en segundo plano mediante `viewModelScope.launch` (Coroutines) para no bloquear el hilo principal.

4. **Jetpack Compose (Capa UI):**
   * Toda la interfaz de usuario (`TaskScreen`) está construida de forma declarativa con Jetpack Compose.
   * La vista observa el estado del ViewModel mediante `collectAsState()`, redibujándose automáticamente y de forma eficiente únicamente cuando los datos reales cambian.

## 🚀 Cómo ejecutar el proyecto

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto utilizando **Android Studio**.
3. Deja que Gradle sincronice las dependencias (asegúrate de tener conexión a internet).
4. Selecciona un emulador (se recomienda API 24 o superior) o conecta un dispositivo físico.
5. Presiona el botón de **Run** (Shift + F10).

---
*Proyecto desarrollado para la Unidad 6: Persistencia de datos.*
