package collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestCollect {

    @Test
    public void testToArray() {
        Object[] objArr = Stream.generate(Math::random).limit(10).toArray();
        Double[] doubleArr = Stream.generate(Math::random).limit(10).toArray(Double[]::new);
        System.out.println();
    }

    @Test
    public void testToCollection() {
        TreeSet<Double> treeSet = Stream.generate(Math::random).limit(
                10
        ).collect(Collectors.toCollection(TreeSet::new));
        Set<Double> doubleSet = Stream.generate(Math::random).limit(10).collect(Collectors.toSet());//HashSet
        List<Double> doubleList = Stream.generate(Math::random).limit(10).collect(Collectors.toList());//ArrayList
        LinkedList<Double> doubleLinkedList = Stream.generate(Math::random).limit(10).collect(Collectors.toCollection(LinkedList::new));
        System.out.println();
    }

    @Test
    public void testToJoinedString() {
        String s = Stream.of("hello", "world", "你好").collect(Collectors.joining());
        System.out.println(s);//helloworld你好
        String s1 = Stream.of("hello", "world", "你好").collect(Collectors.joining(", "));
        System.out.println(s1);//hello, world, 你好
    }

    @Test
    public void testToSummarizing() {
        DoubleSummaryStatistics doubleSummaryStatistics = Stream.generate(Math::random).limit(100).collect(Collectors.summarizingDouble(rec -> rec));
        System.out.println(doubleSummaryStatistics.getMax());
        System.out.println(doubleSummaryStatistics.getAverage());
        System.out.println(doubleSummaryStatistics.getCount());
        System.out.println(doubleSummaryStatistics.getMin());
        System.out.println(doubleSummaryStatistics.getSum());
    }

    class Person {
        private int id;
        private String name;
        private String gender = "male";

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Person(int id, String name, String gender) {
            this.id = id;
            this.name = name;
            this.gender = gender;
        }

        public int getId() {
            return id;
        }

        public Person setId(int id) {
            this.id = id;
            return this;
        }

        public String getName() {
            return name;
        }

        public Person setName(String name) {
            this.name = name;
            return this;
        }

        public String getGender() {
            return gender;
        }

        public Person setGender(String gender) {
            this.gender = gender;
            return this;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }

    @Test
    public void testToMap() {
        List<Person> peoples = Stream.of(new Person(123, "abc"), new Person(456, "def"), new Person(789, "ghi")).collect(Collectors.toList());
        Map<Integer, String> id2name = peoples.stream().collect(Collectors.toMap(Person::getId, Person::getName));
        Map<Integer, String> id2nameTreeMap = peoples.stream().collect(Collectors.toMap(Person::getId, Person::getName));

        Map<Integer, Person> id2Person = peoples.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println();
    }

    @Test
    public void testToMapWithDuplicateKey() {
        List<Person> peoples = Stream.of(new Person(123, "abc"), new Person(456, "def"), new Person(789, "ghi", "female")).collect(Collectors.toList());
        Assert.assertThrows(IllegalStateException.class, () -> peoples.stream().collect(Collectors.toMap(Person::getGender, Function.identity())));
        Map<String, Person> gender2person = peoples.stream().collect(Collectors.toMap(Person::getGender, Function.identity(), (existed, newadded) -> {
            return newadded;
        }));//keep the last record when key is duplicate

        Map<String, Set<TestCollect.Person>> gender2persons = peoples.stream().collect(Collectors.toMap(Person::getGender, Collections::singleton, (rec1, rec2) -> {
            Set<TestCollect.Person> set = new HashSet<>(rec1);
            set.addAll(rec2);
            return set;
        }));//put records with duplicate key to set.
        gender2persons = peoples.stream().collect(Collectors.toMap(Person::getGender, Collections::singleton, (rec1, rec2) -> {
            Set<TestCollect.Person> set = new HashSet<>(rec1);
            set.addAll(rec2);
            return set;
        }, TreeMap::new));//return treemap
        Map<String, List<Person>> persons = peoples.stream().collect(Collectors.groupingBy(Person::getGender));
        Map<String, Map<Integer, String>> gender2id2name = peoples.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.toMap(Person::getId, Person::getName)));
        System.out.println();
    }

    @Test
    public void testParitcitionBy() {
        List<Person> peoples = Stream.of(new Person(123, "abc"), new Person(456, "def"), new Person(789, "ghi", "female")).collect(Collectors.toList());
        Map<Boolean, List<Person>> booleanListMap = peoples.stream().collect(Collectors.partitioningBy(rec -> rec.getGender().equals("male")));
        System.out.println();
    }
}
