package michael.comparable;

class StudentWithRawType implements Comparable{
    private String name;

    public StudentWithRawType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Object o) {
        StudentWithRawType other  = (StudentWithRawType) o;
        return name.compareTo(other.name);
    }
}
