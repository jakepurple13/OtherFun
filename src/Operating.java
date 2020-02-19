import java.util.ArrayList;
import java.util.Arrays;

public class Operating {

    private final ArrayList<String> items = new ArrayList<>();

    public Operating(String... item) {
        items.add("Hello");
        String word = "Hello World";
        for (int i = 0; i < word.length(); i++) {
            items.add("" + word.charAt(i));
        }
        items.addAll(Arrays.asList(item));
    }

    String get(int location) {
        return items.get(location);
    }

    void set(int location, String item) {
        items.set(location, item);
    }

    void plusAssign(String item) {
        items.add(item);
    }

    Operating plus(String item) {
        return new Operating(item);
    }

    boolean contains(String item) {
        return items.contains(item);
    }

    void invoke(String item) {
        plusAssign(item);
    }

    ArrayList<String> getList() {
        return items;
    }

}

class OperatingTest {
    void test() {
        Operating f = new Operating();
    }
}
