import java.util.HashMap;

class Rolodex {

    private static HashMap<String, String> rolodex = new HashMap<String, String>();

    public void addContact(String name, String phoneNumber) { // add contact to HashMap
        rolodex.put(name, phoneNumber);
    }
    public void removeContact(String name) { // remove contact from HashMap
        rolodex.remove(name);
    }
    public String getNumber(String name) { // get an item by key from HashMap
        return rolodex.get(name);
    }
    public HashMap<String, String> getRolodex() {
        return rolodex;
    }
    public boolean isValidPhoneNumber(String phoneNumber) { // checks for valid phone #
        if (phoneNumber.length() != 10) { // check to see if length is right
            return false;
        }
        for (char c : phoneNumber.toCharArray()) { // checks to see if non-digits exist
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public String capitalizeFirstLetter(String fullName) { // capitalizes names for printing
        String[] firstLast = fullName.split("\\s+"); // split name by whitespace into an array
        StringBuilder capitalizedName = new StringBuilder();

        for (String name : firstLast) { // capitalizes first letter of each name
            if (!name.isEmpty()) {
                char firstLetter = Character.toUpperCase(name.charAt(0));
                capitalizedName.append(firstLetter)
                        .append(name.substring(1)).append(" ");
            }
        }
        return capitalizedName.toString().trim();
    }
    public boolean isValidName(String name) { // ensures only letters are used in names
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }
    public String onlyPhoneNumbers(String phoneNumber) { // removes all non-digits from a string
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber;
    }
    public String formattedPhoneNumbers(String phoneNumber) { // formats number to (xxx) xxx-xxxx
        String formattedNumber = "(" + phoneNumber.substring(0, 3) + ") " +
                phoneNumber.substring(3, 6) + "-" +
                phoneNumber.substring(6);
        return formattedNumber;
    }
}
