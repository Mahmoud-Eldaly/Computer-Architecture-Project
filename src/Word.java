public class Word {

    private int word;

    public Word(int word) {
        this.word = word;
    }

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public String toString() {
        return Integer.toString(word);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        return word == word1.word;
    }



}
