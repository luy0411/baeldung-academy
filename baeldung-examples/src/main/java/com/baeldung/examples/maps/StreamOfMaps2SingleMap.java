package com.baeldung.examples.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.swing.UIManager.put;

// from https://www.baeldung.com/java-flatten-stream-map
public class StreamOfMaps2SingleMap {

    private Map<String, Integer> playerMap1;
    private Map<String, Integer> playerMap2;
    private Map<String, Integer> playerMap3;
    private Map<String, Integer> expectedMap;

    public void theProblem() {
        playerMap1 = new HashMap<String, Integer>() {{
            put("Kai", 92);
            put("Liam", 100);
        }};

        playerMap2 = new HashMap<String, Integer>() {{
            put("Eric", 42);
            put("Kevin", 77);
        }};
        playerMap3 = new HashMap<String, Integer>() {{
            put("Saajan", 35);
        }};

        expectedMap = new HashMap<String, Integer>() {{
            put("Saajan", 35);
            put("Liam", 100);
            put("Kai", 92);
            put("Eric", 42);
            put("Kevin", 77);
        }};

        System.out.println("Então vc tem esses mapinhas...");
        System.out.println(playerMap1);
        System.out.println(playerMap2);
        System.out.println(playerMap3);


        System.out.println("\nE quer juntar a caceta toda em um só...");
        System.out.println(expectedMap);
    }

    public void theSolutionWithFlattenMap() {
        Map<String, Integer> mergedMap =
                Stream.of(playerMap1, playerMap2, playerMap3)
                      .flatMap(map -> map.entrySet()
                                         .stream())
                      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("\nflatMap result:");
        System.out.println(mergedMap);
        System.out.println("Igual ao expected? " + expectedMap.equals(mergedMap));
    }
    public void theSolutionWithReduce() {
        Map<String, Integer> mergedMap = Stream.of(playerMap1, playerMap2, playerMap3)
                .reduce(new HashMap<>(), (acc, map) -> {
                    acc.putAll(map);
                    return acc;
                });
    
        System.out.println("\nreduce result:");
        System.out.println(mergedMap);
        System.out.println("Igual ao expected? " + expectedMap.equals(mergedMap));
    }
    
    public void theSolutionWithForEach() {
        Map<String, Integer> mergedMap = new HashMap<>();
        Stream.of(playerMap1, playerMap2, playerMap3)
                .forEach(mergedMap::putAll);
    
        System.out.println("\nforEach result:");
        System.out.println(mergedMap);
        System.out.println("Igual ao expected? " + expectedMap.equals(mergedMap));
    }
    
    public void theSolutionWithMerge() {
        Map<String, Integer> mergedMap = new HashMap<>(playerMap1);
        playerMap2.forEach((key, value) -> mergedMap.merge(key, value, (v1, v2) -> v2));
        playerMap3.forEach((key, value) -> mergedMap.merge(key, value, (v1, v2) -> v2));
    
        System.out.println("\nmerge result:");
        System.out.println(mergedMap);
        System.out.println("Igual ao expected? " + expectedMap.equals(mergedMap));
    }
    
    // Update main method to include new examples
    public static void main(String[] args) {
        StreamOfMaps2SingleMap clazz = new StreamOfMaps2SingleMap();
        clazz.theProblem();
        clazz.theSolutionWithFlattenMap();
        clazz.theSolutionWithReduce();
        clazz.theSolutionWithForEach();
        clazz.theSolutionWithMerge();
    }

}
