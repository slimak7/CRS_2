package SetsModel;

public enum Connector {

    or, and;

    @Override
    public String toString() {

        switch (this) {

            case or -> {
                return "lub";
            }
            case and -> {
                return "i";
            }
            default -> {
                return null;
            }
        }
    }
}
