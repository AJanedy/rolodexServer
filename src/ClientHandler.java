import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class ClientHandler extends Thread {
    private Socket clientSocket;
    private Rolodex rolodex;

    public ClientHandler(Socket socket, Rolodex rolodex) {
        this.clientSocket = socket;
        this.rolodex = rolodex;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientData;
            int clientSelection = 0;
            String[] validSelections = new String[] {"1", "2", "3"};
            while (true) {
                do {
                    out.println("\nWelcome to the Rolodex database.");
                    out.println("Would you like to: \n");
                    out.println("1. STORE a phone number");
                    out.println("2. GET a phone number");
                    out.println("3. REMOVE a phone number");
                    out.println("4. PRINT all names in rolodex.");
                    out.println("5. EXIT the rolodex.\n");
                    out.println("Enter the number of your selection: ");
                    clientData = in.readLine().strip();
                    try {
                        clientSelection = Integer.parseInt(clientData);
                    } catch (NumberFormatException e) {
                        out.println("Invalid Selection");
                    }
                    if (clientSelection == 5) {
                        out.println("Exiting the program");
//                        in.close();
//                        out.close();
                        System.exit(0);
                    }
                    if (clientSelection < 1 || clientSelection > 5) {
                        out.println("Invalid selection.");
                    }
                } while (clientSelection < 1 || clientSelection > 4);
                String phoneNumber;
                String name;

                switch (clientData) {
                    case "1": // STORE a phone number
                        do {
                            System.out.println("Client storing a contact...");
                            out.println("\nSelection: STORE a contact.");
                            out.println("Enter the name of the new contact.");
                            out.println("Press enter when done.");
                            name = in.readLine().toLowerCase();
                            if (!rolodex.isValidName(name)) {
                                out.println("\nInvalid name, must contain only letters and spaces");
                            }
                        } while (!rolodex.isValidName(name));
                        do {
                            out.println("\nEnter the phone number of your contact.");
                            out.println("Press enter when done.");
                            phoneNumber = in.readLine();
                            phoneNumber = rolodex.onlyPhoneNumbers(phoneNumber);
                            if (!rolodex.isValidPhoneNumber(phoneNumber)) {
                                out.println("\nInvalid phone number");
                            }
                        } while (!rolodex.isValidPhoneNumber(phoneNumber));
                        rolodex.addContact(name, phoneNumber);
                        System.out.println(rolodex.capitalizeFirstLetter(name) + " added to rolodex.");
                        out.println("\nContact stored successfully.");
                        out.println("Name: " + rolodex.capitalizeFirstLetter(name));
                        out.println("Number: " + rolodex.formattedPhoneNumbers(phoneNumber));
                        break;

                    case "2": // GET a phone number
                        do {
                            System.out.println("Client accessing a contact.");
                            out.println("\nSelection: GET a contact.");
                            out.println("Enter the contact name you wish to retrieve.");
                            out.println("Press enter when done");
                            name = in.readLine().toLowerCase();
                            if (!rolodex.isValidName(name)) {
                                out.println("\nInvalid name, must contain only letters and spaces");
                            }
                            if (!rolodex.getRolodex().containsKey(name)) {
                                out.println("Contact not found in Rolodex.");
                            }
                        } while (!rolodex.isValidName(name) ||
                                !rolodex.getRolodex().containsKey(name));
                        phoneNumber = rolodex.getNumber(name);
                        name = rolodex.capitalizeFirstLetter(name);
                        phoneNumber = rolodex.formattedPhoneNumbers(phoneNumber);
                        System.out.println("Client performed phone number query on " + name);
                        out.println("\nPhone number for " + name + " is: " + phoneNumber);
                        break;
                    case "3": // REMOVE a contact
                        do {
                            System.out.println("Client removing a contact.");
                            out.println("\nSelection: REMOVE a contact.");
                            out.println("Enter a contact to remove");
                            out.println("Press enter when done");
                            name = in.readLine().toLowerCase();
                            if (!rolodex.isValidName(name)) {
                                out.println("Invalid name, must contain only letters and spaces");
                            }
                            else if (!rolodex.getRolodex().containsKey(name)) {
                                out.println("Contact not found in Rolodex.");
                            }
                        } while (!rolodex.isValidName(name) &&
                                !rolodex.getRolodex().containsKey(name));
                        rolodex.removeContact(name);
                        out.println("\n" + rolodex.capitalizeFirstLetter(name) +
                                " has been removed from the rolodex");
                        System.out.println("Client removed " + rolodex.capitalizeFirstLetter(name) +
                                " from rolodex.");
                        break;
                    case "4": // PRINT the rolodex
                        System.out.println("Client requesting rolodex index");
                        out.println();
                        for (Map.Entry<String, String> entry : rolodex.getRolodex().entrySet()) {
                            out.println(rolodex.capitalizeFirstLetter(entry.getKey()));
                        }
                        break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}
