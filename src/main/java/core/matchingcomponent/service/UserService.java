package core.matchingcomponent.service;

import core.matchingcomponent.model.Users;
import core.matchingcomponent.model.UsersDB;
import core.matchingcomponent.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private static final String API_URL = "https://jsonplaceholder.typicode.com/users";

    public List<Users> getUsers() {
        RestTemplate restTemplate = new RestTemplate();
        Users[] usersArray = restTemplate.getForObject(API_URL, Users[].class);
        return Arrays.asList(usersArray);
    }

    public List<UsersDB> getAllUsers() {
        return userRepository.findAll();
    }

    // Method to store users in another list
    public List<UsersDB> getUsersAsList() {
        List<UsersDB> userList = new ArrayList<>();
        userList.addAll(userRepository.findAll());
        return userList;
    }

    public void matchAndUpdate() {
        List<Users> usersFromJson = getUsers();
        List<UsersDB> usersFromDB = getAllUsers();

        for (UsersDB userFromDB : usersFromDB) {
            for (Users userFromJson : usersFromJson) {
                // Split the address in usersFromDB to extract the components
                String[] addressParts = userFromDB.getAddress().split(", ");

                if (addressParts.length >= 2) { // Ensure the array has at least 2 elements
                    String streetFromDB = addressParts[0]; // Assuming the street is always the first part
                    String[] cityAndZip = addressParts[1].split(" - ");

                    if (cityAndZip.length == 2) { // Ensure the array has 2 elements after splitting
                        String cityFromDB = cityAndZip[0]; // Assuming the city is the first part after splitting
                        String zipCodeFromDB = cityAndZip[1]; // Assuming the zip code is the second part after splitting

                        if (       namesMatch(userFromDB.getFullName(), userFromJson.getName())
                                && emailsMatch(userFromDB.getEmail(), userFromJson.getEmail())
                                && usernamesMatch(userFromDB.getUsername(), userFromJson.getUsername())
                                && phonesMatch(userFromDB.getPhone(), userFromJson.getPhone())
                                && streetsMatch(streetFromDB, userFromJson.getAddress().getStreet())
                                && citiesMatch(cityFromDB, userFromJson.getAddress().getCity())
                                && zipCodesMatch(zipCodeFromDB, userFromJson.getAddress().getZipcode())) {
                            userFromDB.setMatchId(String.valueOf(userFromJson.getId()));
                            userRepository.save(userFromDB);
                            break; // No need to continue checking once a match is found
                        }
                    }
                }
            }
        }
    }


    private boolean namesMatch(String nameDb, String nameJs) {
        // Calculate the Levenshtein distance between the two emails
        if (StringUtils.isBlank(nameDb) && StringUtils.isBlank(nameJs)){
            return true;
        } else if (StringUtils.isBlank(nameDb) || StringUtils.isBlank(nameJs)) {
            return false;
        } else {
            int distance = calculateLevenshteinDistance(nameDb, nameJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(nameDb.length(), nameJs.length());
            return distance <= 0.4 * maxLength;
        }
    }

    private boolean emailsMatch(String emailDb, String emailJs) {
        if (StringUtils.isBlank(emailDb) && StringUtils.isBlank(emailJs)){
            return true;
        } else if (StringUtils.isBlank(emailDb) || StringUtils.isBlank(emailJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(emailDb, emailJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(emailDb.length(), emailJs.length());
            return distance <= 0.4 * maxLength;
        }
    }

    private boolean usernamesMatch(String usernameDb, String usernameJs) {
        if (StringUtils.isBlank(usernameDb) && StringUtils.isBlank(usernameJs)){
            return true;
        } else if (StringUtils.isBlank(usernameDb) || StringUtils.isBlank(usernameJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(usernameDb, usernameJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(usernameDb.length(), usernameJs.length());
            return distance <= 0.4 * maxLength;
        }
    }

    private boolean phonesMatch(String phoneDb, String phoneJs) {
        if (StringUtils.isBlank(phoneDb) && StringUtils.isBlank(phoneJs)){
            return true;
        } else if (StringUtils.isBlank(phoneDb) || StringUtils.isBlank(phoneJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(phoneDb, phoneJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(phoneDb.length(), phoneJs.length());
            return distance <= 0.4 * maxLength;
        }

    }

    private boolean streetsMatch(String streetDb, String streetJs) {
        if (StringUtils.isBlank(streetDb) && StringUtils.isBlank(streetJs)){
            return true;
        } else if (StringUtils.isBlank(streetDb) || StringUtils.isBlank(streetJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(streetDb, streetJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(streetDb.length(), streetJs.length());
            return distance <= 0.4 * maxLength;
        }
    }
    private boolean citiesMatch(String cityDb, String cityJs) {
        if (StringUtils.isBlank(cityDb) && StringUtils.isBlank(cityJs)){
            return true;
        } else if (StringUtils.isBlank(cityDb) || StringUtils.isBlank(cityJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(cityDb, cityJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(cityDb.length(), cityJs.length());
            return distance <= 0.4 * maxLength;
        }
    }

    private boolean zipCodesMatch(String zipCodeDb, String zipCodeJs) {
        if (StringUtils.isBlank(zipCodeDb) && StringUtils.isBlank(zipCodeJs)) {
            return true;
        } else if (StringUtils.isBlank(zipCodeDb) || StringUtils.isBlank(zipCodeJs)) {
            return false;
        } else {
            // Calculate the Levenshtein distance between the two emails
            int distance = calculateLevenshteinDistance(zipCodeDb, zipCodeJs);

            // Assume emails match if the distance is less than or equal to 20% of the length of the longer email
            int maxLength = Math.max(zipCodeDb.length(), zipCodeJs.length());
            return distance <= 0.4 * maxLength;
        }
    }

// Implement similar methods for usernames, phones, streets, cities, and zip codes

    private int calculateLevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }
}


