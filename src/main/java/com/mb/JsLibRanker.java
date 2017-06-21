package com.mb;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsLibRanker {

    final Set<LibraryPattern> libraryPatterns = new HashSet<>();
    final List<LibraryRanking> ranking = new ArrayList<>();

    public void usePattern(String pattern, String libraryName) {
        libraryPatterns.add(new LibraryPattern(pattern, libraryName));
    }

    public List<LibraryRanking> getRanking() {
        Collections.sort(ranking, (o1, o2) -> o2.numberOfOccurrences - o1.numberOfOccurrences);

        return ranking;
    }

    public void consume(String libraryRawName) {
        String libraryName = libraryRawName;

        for (LibraryPattern libraryPattern : libraryPatterns) {
            if (libraryPattern.matches(libraryRawName)) {
                libraryName = libraryPattern.libraryName;
            }
        }

        updateRanking(libraryName);
    }

    private void updateRanking(String libraryName) {
        boolean updated = false;

        Iterator<LibraryRanking> rankingIt = ranking.iterator();
        while (rankingIt.hasNext() && !updated) {
            LibraryRanking currentRanking = rankingIt.next();
            if (currentRanking.libraryName.equals(libraryName)) {
                updated = true;
                currentRanking.numberOfOccurrences++;
            }
        }

        if (!updated) {
            ranking.add(new LibraryRanking(libraryName));
        }
    }

    public static class LibraryRanking {
        public final String libraryName;

        public int numberOfOccurrences;

        private LibraryRanking(String libraryName) {
            this.libraryName = libraryName;
            this.numberOfOccurrences = 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LibraryRanking that = (LibraryRanking) o;

            return libraryName.equals(that.libraryName);
        }

        @Override
        public int hashCode() {
            return libraryName.hashCode();
        }
    }


    private static class LibraryPattern {
        private final Pattern pattern;

        public final String libraryName;

        public LibraryPattern(String pattern, String libraryName) {
            this.pattern = Pattern.compile(pattern);
            this.libraryName = libraryName;
        }

        public boolean matches(String input) {
            Matcher matcher = pattern.matcher(input);
            return matcher.matches();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LibraryPattern that = (LibraryPattern) o;

            return pattern.equals(that.pattern);
        }

        @Override
        public int hashCode() {
            return pattern.hashCode();
        }
    }

}
