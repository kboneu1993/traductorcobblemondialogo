package com.keyli.bcadialoguepatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class TranslationRegistry {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Type MAP_TYPE = new TypeToken<LinkedHashMap<String, String>>() {}.getType();
    private static final LinkedHashMap<String, String> TRANSLATIONS = new LinkedHashMap<>();

    private TranslationRegistry() {}

    public static void load(Path path) {
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                writeDefaultFile(path);
            }
            try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                Map<String, String> loaded = GSON.fromJson(reader, MAP_TYPE);
                TRANSLATIONS.clear();
                if (loaded != null) {
                    TRANSLATIONS.putAll(loaded);
                }
            }
        } catch (IOException e) {
            BcaDialoguePatcherClient.LOGGER.error("No se pudo cargar {}", path, e);
            TRANSLATIONS.clear();
            TRANSLATIONS.putAll(defaultTranslations());
        }
    }

    public static int size() {
        return TRANSLATIONS.size();
    }

    public static Optional<String> translate(String original) {
        if (original == null || original.isBlank()) {
            return Optional.empty();
        }
        String exact = TRANSLATIONS.get(original);
        if (exact != null) {
            return Optional.of(exact);
        }

        for (Map.Entry<String, String> entry : TRANSLATIONS.entrySet()) {
            String source = entry.getKey();
            if (source.contains("${player}") || source.contains("${npc}")) {
                String translated = tryTemplate(source, entry.getValue(), original);
                if (translated != null) {
                    return Optional.of(translated);
                }
            }
        }
        return Optional.empty();
    }

    private static String tryTemplate(String source, String target, String original) {
        if (source.equals("Hello, ${player}! I'm ${npc}!")) {
            String regex = "^Hello, (.+)! I'm (.+)!$";
            var m = java.util.regex.Pattern.compile(regex).matcher(original);
            if (m.matches()) {
                return target.replace("${player}", m.group(1)).replace("${npc}", m.group(2));
            }
        }
        if (source.equals("Welcome to Silverpine Lodge, ${player}. I'm the ${npc}.")) {
            String regex = "^Welcome to Silverpine Lodge, (.+)\\. I'm the (.+)\\.$";
            var m = java.util.regex.Pattern.compile(regex).matcher(original);
            if (m.matches()) {
                return target.replace("${player}", m.group(1)).replace("${npc}", m.group(2));
            }
        }
        if (source.equals("Welcome to Wyrm’s Rest, ${player}. I'm the ${npc}.")) {
            String regex = "^Welcome to Wyrm.’?s Rest, (.+)\\. I'm the (.+)\\.$";
            var m = java.util.regex.Pattern.compile(regex).matcher(original);
            if (m.matches()) {
                return target.replace("${player}", m.group(1)).replace("${npc}", m.group(2));
            }
        }
        return null;
    }

    private static void writeDefaultFile(Path path) throws IOException {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(defaultTranslations(), MAP_TYPE, writer);
        }
    }

    public static LinkedHashMap<String, String> defaultTranslations() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Welcome to the Pokemon Center! We will heal you and your Pokemon.", "¡Bienvenido al Centro Pokémon! Curaremos a tus Pokémon.");
        map.put("I would offer to heal your Pokémon, but I can't seem to find my healing machine...", "Te curaría los Pokémon, pero no consigo encontrar mi máquina de curación...");
        map.put("I'm sorry about that!", "¡Lo siento mucho!");
        map.put("I would offer to heal your Pokémon for you, but your Pokémon are completely healthy!", "Te curaría los Pokémon, pero están completamente sanos.");
        map.put("I'd like to heal your Pokémon but my machine is busy! Please come back later!", "Me gustaría curar a tus Pokémon, pero mi máquina está ocupada. ¡Vuelve más tarde!");
        map.put("Would you like me to heal your Pokémon?", "¿Quieres que cure a tus Pokémon?");
        map.put("Yes", "Sí");
        map.put("No", "No");
        map.put("Cancel", "Cancelar");
        map.put("Goodbye", "Adiós");
        map.put("Back", "Atrás");
        map.put("Start Battle", "Iniciar combate");
        map.put("Battle", "Combatir");
        map.put("About Me", "Sobre mí");
        map.put("Facility Rundown", "Instalaciones");
        map.put("About the Lodge", "Sobre el Lodge");
        map.put("Restaurant", "Restaurante");
        map.put("Tavern", "Taberna");
        map.put("More...", "Más...");
        map.put("Spa and Pool", "Spa y piscina");
        map.put("Library", "Biblioteca");
        map.put("Lounge", "Salón");
        map.put("About the Rest", "Sobre el lugar");
        map.put("Who Are You?", "¿Quién eres?");
        map.put("Redstone", "Redstone");
        map.put("Sorting System", "Sistema de clasificación");
        map.put("Elevator", "Ascensor");
        map.put("Auto-Processors", "Procesadores automáticos");
        map.put("Iron Farm", "Granja de hierro");
        map.put("Bone Meal", "Harina de hueso");
        map.put("Farms", "Granjas");
        map.put("Unfinished Farm", "Granja inacabada");
        map.put("Treasure...", "Tesoro...");
        map.put("Record", "Récord");
        map.put("Twitch", "Twitch");
        map.put("About Wyrms Rest", "Sobre Wyrm’s Rest");
        map.put("About BCA", "Sobre BCA");
        map.put("Vision", "Visión");
        map.put("Buried Seeds", "Semillas enterradas");
        map.put("Secret Hole...", "Rincón secreto...");
        map.put("Challenge", "Desafiar");
        map.put("Who are you?", "¿Quién eres?");
        map.put("Not yet.", "Aún no.");
        map.put("Power is common. Preparation is rare.", "El poder es común. La preparación es rara.");
        map.put("Let's see how your team is engineered.", "Veamos cómo está diseñado tu equipo.");
        map.put("I am the first gate.", "Soy la primera puerta.");
        map.put("Every system fails somewhere. I find where yours does.", "Todo sistema falla en algún punto. Yo encuentro dónde falla el tuyo.");
        map.put("If you can't beat me, the rest of the Elite Four isn't for you.", "Si no puedes vencerme, el resto del Alto Mando no es para ti.");
        map.put("I'll prepare first.", "Primero me prepararé.");
        map.put("Smart. Recognizing when you're underprepared is the first step.", "Inteligente. Reconocer que no estás preparado es el primer paso.");
        map.put("Hello, ${player}! I'm ${npc}!", "¡Hola, ${player}! ¡Soy ${npc}!");
        map.put("Welcome to Silverpine Lodge, ${player}. I'm the ${npc}.", "Bienvenido a Silverpine Lodge, ${player}. Soy ${npc}.");
        map.put("Welcome to Wyrm’s Rest, ${player}. I'm the ${npc}.", "Bienvenido a Wyrm’s Rest, ${player}. Soy ${npc}.");
        return map;
    }
}
