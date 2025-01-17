package www.legendarycommunity.com.br.legendary_classes.sistemas;

import java.util.HashMap;
import java.util.Map;

public class upleve {

    public static class ClassData {

        private final int cost;

        public ClassData(
                int cost
        ) {
            this.cost = cost;
        }
        
        public int getCost() {
            return cost;
        }
    }

    private static final Map<String, Map<Integer, ClassData>> classRequirements = new HashMap<>();

    static {
        // Configura os requisitos por nível para a classe "Humano"
        Map<Integer, ClassData> humanoRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 50000 * level;

            humanoRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Humano", humanoRequirements);

        // Configura os requisitos por nível para a classe "HumanoMercador"
        Map<Integer, ClassData> humanoMercadorRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 100000 * level;

            humanoMercadorRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("HumanoMercador", humanoMercadorRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> humanoNobreRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 150000 * level;

            humanoNobreRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("HumanoNobre", humanoNobreRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> lamiaRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 400000 * level;

            lamiaRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Lamia", lamiaRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> nosferatuRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 450000 * level;

            nosferatuRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Nosferatu", nosferatuRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> vampiroReiRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 500000 * level;

            vampiroReiRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("VampiroRei", vampiroReiRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> demonioRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
             int cost = 550000 * level;

            demonioRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Demonio", demonioRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> albionRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 600000 * level;

            albionRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Albion", albionRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> principeInfernalRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 650000 * level;

            principeInfernalRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("PrincipeInfernal", principeInfernalRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> necromancerRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 700000 * level;

            necromancerRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Necromancer", necromancerRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> mortoVivoRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 750000 * level;

            mortoVivoRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("MortoVivo", mortoVivoRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> overlordRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 800000 * level;

            overlordRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Overlord", overlordRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> cavaleiroDaFomeRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 850000 * level;

            cavaleiroDaFomeRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("CavaleiroDaFome", cavaleiroDaFomeRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> cavaleiroDaPesteRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 900000 * level;

            cavaleiroDaPesteRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("CavaleiroDaPeste", cavaleiroDaPesteRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> cavaleiroDaGuerraRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 1000000 * level;

            cavaleiroDaGuerraRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("CavaleiroDaGuerra", cavaleiroDaGuerraRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> cavaleiroDaMorteRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
             int cost = 2000000 * level;

            cavaleiroDaMorteRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("CavaleiroDaMorte", cavaleiroDaMorteRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> anaoRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 3000000 * level;

            anaoRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Anao", anaoRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> anaoMercadorRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 3500000 * level;

            anaoMercadorRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("AnaoMercador", anaoMercadorRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> anaoNobreRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 4000000 * level;

            anaoNobreRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("AnaoNobre", anaoNobreRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> druidaRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 4500000 * level;

            druidaRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Druida", druidaRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> driadeRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 5000000 * level;

            driadeRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Driade", driadeRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> tuathaDeDanannRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 5500000 * level;

            tuathaDeDanannRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("TuathaDeDanann", tuathaDeDanannRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> poseidonRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 6000000 * level;

            poseidonRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Poseidon", poseidonRequirements);

        // Configura os requisitos por nível para a classe "HumanoNobre"
        Map<Integer, ClassData> aresRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 7000000 * level;

            aresRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Ares", aresRequirements);

        // Configura os requisitos por nível para a classe "Mago"
        Map<Integer, ClassData> magoRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 8000000 * level;

            magoRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("Mago", magoRequirements);

        // Configura os requisitos por nível para a classe "MagoRegente"
        Map<Integer, ClassData> magoRegenteRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 9000000 * level;

            magoRegenteRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("MagoRegente", magoRegenteRequirements);

        // Configura os requisitos por nível para a classe "MagoRegente"
        Map<Integer, ClassData> ReiMagoRequirements = new HashMap<>();
        for (int level = 1; level <= 100; level++) {
            int cost = 10000000 * level;

            ReiMagoRequirements.put(level, new ClassData(cost));
        }
        classRequirements.put("ReiMago", ReiMagoRequirements);

    }

    public static ClassData getClassData(String className, int level) {
        Map<Integer, ClassData> requirements = classRequirements.get(className);
        if (requirements != null) {
            return requirements.get(level);
        }
        return null;
    }
}
