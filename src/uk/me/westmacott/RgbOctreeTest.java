package uk.me.westmacott;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;


public class RgbOctreeTest {

    static Random random = new Random();
    static long timeA = 0;
    static long timeB = 0;
    static long timeC = 0;
    static long matchesB = 0;
    static long matchesC = 0;
    static long failsB = 0;
    static long failsC = 0;

    static double totalErrorB = 0;
    static double minErrorB = Integer.MAX_VALUE;
    static double maxErrorB = Integer.MIN_VALUE;
    static double totalErrorC = 0;
    static double minErrorC = Integer.MAX_VALUE;
    static double maxErrorC = Integer.MIN_VALUE;

    public static void main(String[] args) {


//        RgbOctree testSubject1 = new RgbOctree();
//
//        int rgb = generateColour();
//        testSubject1.add(rgb);
//        testSubject1.add(rgb);
////        testSubject.remove(rgb);
////        testSubject.remove(rgb);
//
//        for (int i = 0; i < testSubject1.colourCount; i++) {
//            if (testSubject1.rgbByIndex[i] != -1) {
//                System.out.println("testSubject.colours["+i+"] = " + testSubject1.rgbByIndex[i]);
//            }
//        }
//
//        System.out.println("Depth: " + testSubject1.counts.length);
//
//        int size = 1;
//        for (int depth = 0; depth < RgbOctree.DEPTH; depth++) {
//
//            System.out.println("Length: " + testSubject1.counts[depth].length);
//
//            for (int i = 0; i < size; i++) {
//                if (testSubject1.counts[depth][i] != 0) {
//                    System.out.println("testSubject.counts["+depth+"]["+i+"] = " + testSubject1.counts[depth][i]);
//                }
//            }
//            size *= 8;
//        }




//        System.out.println("Distance: " + distance(0, 0, 0, 3, 4, 0));


//        Color color = new Color(255, 0, 0);
//        int rgb = color.getRGB() & 0xFFFFFF;

//        tree.add(new Color(255, 0, 0).getRGB());
//        tree.add(new Color(255, 1, 0).getRGB());

        RgbOctree tree1 = new RgbOctree();
        RgbOctree tree2 = new RgbOctree();
        colours = new HashSet<>();
//        tree2.showDebugPane();

//        Set<Integer> doubleCheck = new HashSet<>();

        for (int i = 0; i < 20000; i++) {
            int x = generateColour();
            tree1.add(x);
            tree2.add(x);
            add(x);
//            doubleCheck.add(x);
        }
        System.out.println("Initial sizes: " + colours.size() + " / " + tree1.size() + " / " + tree2.size());

        for (int j = 0; j < 10000; j++) {

            for (int i = 0; i < 10; i++) {
                Object[] array = colours.toArray();
                int aColour = (Integer)array[random.nextInt(array.length)];
                remove(aColour);
                tree1.remove(aColour);
                tree2.remove(aColour);

                if (colours.size() != tree1.size()) {
                    System.out.println("Sizes: " + colours.size() + " / " + tree1.size() + " / " + tree2.size());
                }

                int x = generateColour();
                tree1.add(x);
                tree2.add(x);
                add(x);

                if (colours.size() != tree1.size()) {
                    System.out.println("Sizes: " + colours.size() + " / " + tree1.size() + " / " + tree2.size());
                }
            }

            int target = generateColour();

            long a = System.currentTimeMillis();
            int resultA = nearestTo(target);
            long b = System.currentTimeMillis();
            int resultB = tree1.probablyNearTo(target);
            long c = System.currentTimeMillis();
            int resultC = tree2.nearestTo(target);
            long d = System.currentTimeMillis();


            if (resultB != resultA) {

                double distanceA = distance(resultA, target);
                double distanceB = distance(resultB, target);
                double distanceC = distance(resultC, target);
                double deltaB = distanceB - distanceA;
                double deltaC = distanceC - distanceA;

                if (deltaB > deltaC) {
//                    System.out.println("C wins");
                } else if (deltaC > deltaB) {
//                    System.out.println("B wins");

                    int resultB2 = tree1.probablyNearTo(target);
                    int resultC2 = tree2.nearestTo(target);

                    Integer.toString(resultB2 + resultC2);

                } else {
//                    System.out.println("Draw");
                }

            }


            timeA += (b - a);
            timeB += (c - b);
            timeC += (d - c);

            if (resultA == resultB) {
                matchesB++;
            }
            else {

                double distanceA = distance(resultA, target);
                double distanceB = distance(resultB, target);
                double difference = distanceB - distanceA;

                if (difference < 0) {
                    System.out.println();
                    System.out.println("DIFF!!!");
                    System.out.println("target  = " + new Color(target));
                    System.out.println("resultA = " + new Color(resultA));
                    System.out.println("resultB = " + new Color(resultB));
                    System.out.println("distA   = " + distanceA);
                    System.out.println("distB   = " + distanceB);
                    System.out.println("diff    = " + difference);
                    System.out.println(colours.contains(resultA));
//                    System.out.println(doubleCheck.contains(result1));
                }

                if (difference == 0) {
                    matchesB++;
                }
                else {
                    totalErrorB += difference;
                    minErrorB = Math.min(minErrorB, difference);
                    maxErrorB = Math.max(maxErrorB, difference);
                    failsB++;
                }
            }

            if (resultA == resultC) {
                matchesC++;
            }
            else {

                double distanceA = distance(resultA, target);
                double distanceC = distance(resultC, target);
                double difference = distanceC - distanceA;

                if (difference < 0) {
                    System.out.println();
                    System.out.println("DIFF!!!");
                    System.out.println("target  = " + new Color(target));
                    System.out.println("resultA = " + new Color(resultA));
                    System.out.println("resultC = " + new Color(resultC));
                    System.out.println("distA   = " + distanceA);
                    System.out.println("distC   = " + distanceC);
                    System.out.println("diff    = " + difference);
                    System.out.println(colours.contains(resultA));
//                    System.out.println(doubleCheck.contains(result1));
                }

                if (difference == 0) {
                    matchesC++;
                }
                else {
                    totalErrorC += difference;
                    minErrorC = Math.min(minErrorC, difference);
                    maxErrorC = Math.max(maxErrorC, difference);
                    failsC++;
                }
            }

            System.out.print("\r" + j);
        }

        System.out.println();
        
        System.out.println("TryAll: " + Main.display(timeA));

        System.out.println("Octree approx.: " + Main.display(timeB) + " Success: " + ((matchesB * 100.0) / (matchesB + failsB)) + "% (" + matchesB + " : " + failsB + ")");
        System.out.println("Average Error: " + (totalErrorB / failsB));
        System.out.println("Max / Min : " + maxErrorB + " / " + minErrorB);

        System.out.println("Octree exact.: " + Main.display(timeC) + " Success: " + ((matchesC * 100.0) / (matchesC + failsC)) + "% (" + matchesC + " : " + failsC + ")");
        System.out.println("Average Error: " + (totalErrorC / failsC));
        System.out.println("Max / Min : " + maxErrorC + " / " + minErrorC);

    }

    private static int generateColour() {
//        int r = random.nextInt(256);
//        int g = random.nextInt(256);
////        int b = random.nextInt(256);
//
//        int r = (int) (random.nextDouble() * 256);
//        int g = (int) (random.nextDouble() * 256);
//        int b = (int) (random.nextDouble() * 256);
//        return new Color(r, g, b).getRGB() & 0xFFFFFF;
//
        return new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()).getRGB() & 0xFFFFFF;
    }


    static HashSet<Integer> colours;
    static void add(int colour) {
        colours.add(colour);
    }
    static void remove(int colour) {
        colours.remove(colour);
    }
    static int nearestTo(int colour) {
        int best = 0;
        double leastDistance = Integer.MAX_VALUE;
        for (int candidate : colours) {
            double distance = distance(colour, candidate);
            if (distance < leastDistance) {
                leastDistance = distance;
                best = candidate;
            }
        }
        return best;
    }

    public static double distance(int rgb1, int rgb2) {
        int red1 = (rgb1 >> 16) & 0xFF;
        int green1 = (rgb1 >> 8) & 0xFF;
        int blue1 = rgb1 & 0xFF;
        int red2 = (rgb2 >> 16) & 0xFF;
        int green2 = (rgb2 >> 8) & 0xFF;
        int blue2 = rgb2 & 0xFF;
        return distance(red1, green1, blue1, red2, green2, blue2);
    }

    public static double distance(int red1, int green1, int blue1, int red2, int green2, int blue2) {
        int dr = red1 - red2;
        int dg = green1 - green2;
        int db = blue1 - blue2;
        return Math.pow(dr * dr + dg * dg + db * db, 0.5);
    }



}