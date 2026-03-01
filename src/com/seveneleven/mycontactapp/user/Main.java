package com.seveneleven.mycontactapp.user;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		UserRepository userRepo = new UserRepository();
		ContactRepository contactRepo = new ContactRepository();

		RegistrationService registration = new RegistrationService(userRepo);
		AuthenticationStrategy basicAuth = new BasicAuthStrategy(userRepo);
		AuthenticationStrategy oauthAuth = new OAuthStrategy(userRepo);
		ProfileService profileService = new ProfileService();
		ContactService contactService = new ContactService(contactRepo);

		User currentUser = null;

		try (Scanner sc = new Scanner(System.in)) {

			boolean running = true;
			System.out.println("=== MyContacts ===");

			while (running) {

				System.out.println();

				if (currentUser != null) {

					System.out.println("Logged in: " + currentUser.getName()
					+ " <" + currentUser.getEmail() + "> [" + currentUser.getPlan() + "]");
					System.out.println("Perks: " + currentUser.perks());

					System.out.println("1) Change Display Name");
					System.out.println("2) Change Email");
					System.out.println("3) Change Password");
					System.out.println("4) Create Contact");
					System.out.println("5) List All Contacts");
					System.out.println("6) View Contact by Name");
					System.out.println("7) Edit Contact");
					System.out.println("8) Delete Contact");
					System.out.println("9) Search Contacts");
					System.out.println("10) Filter Contacts");
					System.out.println("11) Logout");
					System.out.println("12) Exit");
					System.out.print("> ");

					String choice = readTrim(sc);

					switch (choice) {
					case "1" -> {
						System.out.print("New Display Name: ");
						String newName = readTrim(sc);
						try {
							profileService.changeDisplayName(currentUser, newName);
							System.out.println("Display name updated.");
						} catch (Exception ex) {
							System.out.println("Update failed: " + ex.getMessage());
						}
					}

					case "2" -> {
						System.out.print("New Email: ");
						String newEmail = readTrim(sc);
						try {
							profileService.changeEmail(currentUser, newEmail, userRepo);
							System.out.println("Email updated.");
						} catch (Exception ex) {
							System.out.println("Update failed: " + ex.getMessage());
						}
					}

					case "3" -> {
						System.out.print("Current Password: ");
						String oldPw = readTrim(sc);

						System.out.print("New Password: ");
						String newPw = readTrim(sc);

						boolean ok = profileService.changePassword(currentUser, oldPw, newPw);
						System.out.println(ok ? "Password changed." : "Password change failed.");
					}

					case "4" -> {
						System.out.print("Name: ");
						String name = readTrim(sc);

						System.out.print("Phone: ");
						String phone = readTrim(sc);

						System.out.print("Email: ");
						String email = readTrim(sc);

						System.out.print("Tag (Family, Work, Gym, etc): ");
						String tag = readTrim(sc);

						try {
							Contact c = contactService.createContact(name, phone, email, tag);
							System.out.println("Created: " + c);
						} catch (Exception ex) {
							System.out.println("Create failed: " + ex.getMessage());
						}
					}
					case "5" -> {
						List<Contact> list = contactService.getAllContacts();

						if (list.isEmpty()) {
							System.out.println("(no contacts yet)");
						} else {
							for (Contact c : list) {
								System.out.println("- " + c);
							}
						}
					}

					case "6" -> {
						System.out.print("Enter Contact Name: ");
						String name = readTrim(sc);

						try {
							Contact c = contactService.viewContactByName(name);

							System.out.println("Tag         : " + c.getTag());
							System.out.println("Name        : " + c.getDisplayName());
							System.out.println("Email       : " + (c.getEmail() == null ? "-" : c.getEmail()));
							System.out.println("Phone       : " + (c.getPhone() == null ? "-" : c.getPhone()));
							System.out.println("Date Added  : " + c.getDateAdded());
							System.out.println("View Count  : " + c.getContactCount());

						} catch (Exception ex) {
							System.out.println("Error: " + ex.getMessage());
						}
					}

					case "7" -> {
						System.out.print("Enter Contact Name: ");
						String name = readTrim(sc);

						System.out.print("New Phone (leave blank to skip): ");
						String newPhone = readTrim(sc);

						System.out.print("New Email (leave blank to skip): ");
						String newEmail = readTrim(sc);

						System.out.print("New Tag (leave blank to skip): ");
						String newTag = readTrim(sc);

						try {
							Contact updated =
									contactService.editContactByName(name, newPhone, newEmail, newTag);

							System.out.println("Contact updated successfully.");
							System.out.println(updated);

						} catch (Exception ex) {
							System.out.println("Edit failed: " + ex.getMessage());
						}
					}
					case "8" -> {
				        System.out.print("Enter Contact Name: ");
				        String name = readTrim(sc);

				        try {
				            contactService.deleteContactByName(name);
				            System.out.println("Contact deleted.");
				        } catch (Exception ex) {
				            System.out.println("Delete failed: " + ex.getMessage());
				        }
				    }

				    case "9" -> {

				        System.out.println("Search By:");
				        System.out.println("1) Name");
				        System.out.println("2) Phone");
				        System.out.println("3) Email");
				        System.out.println("4) Tag");
				        System.out.print("> ");

				        String option = readTrim(sc);
				        String type = null;

				        switch (option) {
				            case "1" -> type = "name";
				            case "2" -> type = "phone";
				            case "3" -> type = "email";
				            case "4" -> type = "tag";
				            default -> System.out.println("Invalid choice");
				        }

				        if (type != null) {
				            System.out.print("Enter search value: ");
				            String value = readTrim(sc);

				            try {
				                List<Contact> results =
				                        contactService.searchContacts(type, value);

				                if (results.isEmpty()) {
				                    System.out.println("No contacts found.");
				                } else {
				                    for (Contact c : results) {
				                        System.out.println("- " + c);
				                    }
				                }

				            } catch (Exception ex) {
				                System.out.println("Search failed: " + ex.getMessage());
				            }
				        }
				    }

					case "10" -> {

						System.out.println("Filter By:");
						System.out.println("1) Tag");
						System.out.println("2) Recent (Newest First)");
						System.out.println("3) Frequently Contacted");
						System.out.print("> ");

						String option = readTrim(sc);
						String type = null;
						String value = null;

						switch (option) {
						case "1" -> {
							type = "tag";
							System.out.print("Enter tag: ");
							value = readTrim(sc);
						}
						case "2" -> type = "recent";
						case "3" -> type = "frequent";
						default -> System.out.println("Invalid choice");
						}

						if (type != null) {
							try {
								List<Contact> results =
										contactService.filterContacts(type, value);

								if (results.isEmpty()) {
									System.out.println("No contacts found.");
								} else {
									for (Contact c : results) {
										System.out.println("- " + c);
									}
								}

							} catch (Exception ex) {
								System.out.println("Filter failed: " + ex.getMessage());
							}
						}
					}

					case "11" -> {
						currentUser = null;
						System.out.println("Logged out.");
					}

					case "12" -> {
						running = false;
						System.out.println("Bye!");
					}

					default -> System.out.println("Invalid choice.");
					}

				} else {
					System.out.println("1) Register");
					System.out.println("2) Login (email + password)");
					System.out.println("3) Login (display name + PIN)");
					System.out.println("4) Exit");
					System.out.print("> ");

					String choice = readTrim(sc);

					switch (choice) {

					case "1" -> handleRegistration(sc, registration);

					case "2" -> {
						System.out.print("Email: ");
						String email = readTrim(sc);
						System.out.print("Password: ");
						String password = readTrim(sc);

						User u = basicAuth.authenticate(email, password);

						if (u != null) {
							currentUser = u;
							System.out.println("Logged in.");
						} else {
							System.out.println("Login failed.");
						}
					}

					case "3" -> {
						System.out.print("Display Name: ");
						String name = readTrim(sc);
						System.out.print("PIN: ");
						String pin = readTrim(sc);

						User u = oauthAuth.authenticate(name, pin);

						if (u != null) {
							currentUser = u;
							System.out.println("Logged in.");
						} else {
							System.out.println("Login failed.");
						}
					}

					case "4" -> {
						running = false;
						System.out.println("Bye!");
					}

					default -> System.out.println("Invalid choice.");
					}
				}
			}
		}
	}

	private static void handleRegistration(Scanner sc, RegistrationService registration) {

		System.out.print("Plan [FREE|PREMIUM]: ");
		String planStr = readTrim(sc).toUpperCase();

		System.out.print("Display Name: ");
		String name = readTrim(sc);

		System.out.print("Email: ");
		String email = readTrim(sc);

		System.out.print("Password: ");
		String password = readTrim(sc);

		UserProfile profile = new UserProfile(null, null, null);

		try {
			User.Plan plan = User.Plan.valueOf(planStr);
			User user = registration.register(name, email, password, plan, profile);

			System.out.println("Registered -> " + user);
			System.out.println("Perks: " + user.perks());
			System.out.println("Your PIN: " + user.getPin());

		} catch (Exception ex) {
			System.out.println("Registration Failed: " + ex.getMessage());
		}
	}

	private static String readTrim(Scanner sc) {
		try {
			String s = sc.nextLine();
			return (s == null) ? "" : s.trim();
		} catch (Exception e) {
			return "";
		}
	}
}