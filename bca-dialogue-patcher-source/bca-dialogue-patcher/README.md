# BCA Dialogue Patcher

Mod cliente para Fabric 1.21.1 que reemplaza en tiempo real textos literales de Brocraft Cobblemon Additions (BCA) sin editar el `.jar` original.

## Qué hace
- Intercepta textos creados con `Text.literal(...)` y `Text.of(...)`.
- Sustituye frases inglesas exactas por sus equivalentes en español.
- Genera un archivo de configuración editable en:
  `config/bcadialoguepatcher/translations.json`

## Qué cubre
- Diálogos de NPC como Nurse Joy.
- Botones y menús que BCA cree como texto literal.
- Frases con plantillas simples como `Hello, ${player}! I'm ${npc}!`.

## Requisitos
- Minecraft 1.21.1
- Fabric Loader
- Fabric API
- Java 21

## Compilar
Abre la carpeta del proyecto en una terminal y ejecuta:

```bash
./gradlew build
```

El `.jar` compilado saldrá en:

```text
build/libs/bca-dialogue-patcher-1.0.0.jar
```

## Instalar
1. Copia el `.jar` compilado a `.minecraft/mods`
2. Mantén también `cobblemon-additions-4.3.0.jar` en `.minecraft/mods`
3. Inicia el juego una vez para que se cree el archivo:
   `config/bcadialoguepatcher/translations.json`
4. Cierra el juego, edita ese JSON si quieres más traducciones y vuelve a abrirlo.

## Añadir más frases
Cada entrada es:

```json
{
  "texto original en inglés": "texto traducido al español"
}
```

## Notas
- Este mod es cliente. Para un mundo en singleplayer basta con instalarlo en tu cliente.
- Si una frase sigue saliendo en inglés, añade la frase exacta al JSON.
- Si BCA usa `Text.translatable(...)` para alguna pantalla, eso se traduce mejor con resource pack, no con este mod.
