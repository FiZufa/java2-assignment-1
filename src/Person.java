import java.util.Objects;

public class Person {
    private String name;
    private String sex;
    private String born;
    private float height;
    private float weight;
    private String country;
    private String countryCode;
    private double bmi;
    private int age;

    public Person(String name, String sex, String born, float height, float weight, String country, String countryCode){
        this.name = name;
        this.sex = sex;
        this.born = born;
        this.height = height;
        this.weight = weight;
        this.country = country;
        this.countryCode = countryCode;

        if (born != null) {
            String[] parts = this.born.split(" ");
            if (parts.length >= 3) {
                try {
                    this.age = 2020 - Integer.parseInt(parts[2]); // Use parsed parts
                } catch (NumberFormatException e) {
                    this.age = 0; // Default to 0 if parsing fails
                }
            } else {
                this.age = 0;
            }
        } else {
            this.age = 0; // Default if born is null
        }


        if (this.weight > 0 && this.height > 0.1) {
            this.bmi = (this.weight / (this.height/100.0*this.height/100.0));
        } else {
            this.bmi = 0.0f;
        }

    }
    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public float getHeight() {
        return height;
    }
    public float getWeight() {
        return weight;
    }

    public double getBmi() {
        return bmi;
    }
    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", born='" + born + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", country='" + country + '\'' +
                ", BMI= " + bmi +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Person person = (Person) o;
//        return name.equals(person.name) &&
//                sex.equals(person.sex) &&
//                age == person.age &&
//                country.equals(person.country);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, sex, age, country);
//    }

}
