# Tienda — Inventario y Ventas

App de inventario y ventas para Android, hecha en HTML/JS y empaquetada con Capacitor.
El APK se genera automáticamente con GitHub Actions: no necesitas instalar Android Studio
ni nada en tu computadora.

## Cómo subir esto a GitHub

1. Crea un repositorio nuevo en GitHub (puede ser público o privado), por ejemplo `tienda-inventario`.
2. Sube **todo el contenido de esta carpeta** (manteniendo las carpetas `www/` y `.github/` tal cual)
   a la raíz de ese repositorio. Puedes arrastrar los archivos desde la web de GitHub
   ("Add file" → "Upload files") o usar git desde la terminal.
3. En cuanto el repositorio quede con estos archivos en la rama `main`, ve a la pestaña
   **Actions** del repo: el flujo "Build APK" debería arrancar solo. Tarda unos minutos.
4. Cuando termine (ícono verde ✅), ve a la pestaña **Releases** del repositorio: ahí vas
   a encontrar el archivo `app-debug.apk` listo para descargar e instalar en tu teléfono
   (tendrás que permitir "Instalar apps de orígenes desconocidos" la primera vez).

Si el flujo falla (ícono rojo ❌), entra al detalle de la ejecución en Actions, copia el
error que aparezca en rojo y lo revisamos juntos.

## Estructura del proyecto

```
tienda-inventario/
├── .github/workflows/build-apk.yml   -> el robot que compila el APK en GitHub
├── www/index.html                     -> toda la app (HTML + CSS + JS en un solo archivo)
├── capacitor.config.json              -> nombre de la app, ID del paquete, carpeta web
├── package.json                       -> dependencias de Capacitor
└── .gitignore
```

La carpeta `android/` **no está incluida a propósito**: Capacitor la genera automáticamente
en cada compilación (paso "Agregar la plataforma Android" del flujo), así que cada vez que
subas un cambio se regenera desde cero, sin arrastrar archivos viejos.

## Cómo hacer cambios después

Para actualizar la app, solo tienes que reemplazar `www/index.html` por la nueva versión
y volver a subirlo a GitHub (o pegarlo directamente en la web de GitHub, editando el
archivo). El flujo de Actions se vuelve a ejecutar solo y genera un nuevo APK en Releases.

## Datos sobre esta versión

- **Nombre de la app:** Tienda
- **ID del paquete (appId):** `com.tienda.inventario`
- El APK que genera este flujo es un **APK de depuración (debug), sin firmar**. Se instala
  y funciona perfectamente para uso personal o de prueba. Si más adelante quieres publicarla
  en Google Play o firmarla con tu propia clave, se puede ajustar el flujo para eso.
- Los datos de la tienda (productos, ventas, fotos, ID de tienda) se guardan solo en el
  propio teléfono; todavía no está conectada a ninguna base de datos en la nube.
