# ✈️ Reservia – Universal Booking System

\<p align="center">
&#x20; \<strong>One Platform. Multiple Destinations. Seamless Bookings.\</strong>
\</p>

\<p align="center">
&#x20; A Java-based desktop application that brings multiple booking services together in one convenient platform.
\</p>

---

## 📌 About The Project

**Reservia** is a Universal Booking System developed using Java Swing and MySQL. It provides users with a single desktop application to explore, book, and manage different services, including travel, movies, hotels, and restaurants.

The application features an interactive graphical interface, an integrated wallet system, booking management, transaction tracking, and ticket generation.

The main objective of Reservia is to simplify the booking experience by bringing multiple services under one platform.

---

## 📸 Application Preview

\<!-- Add your application screenshots here -->

| Home Page | Wallet |
| <img width="1600" height="876" alt="image" src="https://github.com/user-attachments/assets/551e4ae5-d855-4e90-9c5e-49020acc498b" />
 | <img width="1600" height="876" alt="image" src="https://github.com/user-attachments/assets/d743ede6-15cb-498e-8e9b-a697efe4d591" />
 |
|           |        |

| Booking Management | Travel Selection |
| <img width="1600" height="876" alt="image" src="https://github.com/user-attachments/assets/76ea6d19-ce01-4a66-807b-6bcf473d96e1" />
 | <img width="1600" height="876" alt="image" src="https://github.com/user-attachments/assets/8fa6ef5b-6f82-4281-aac3-3b45bffb528b" />
 |
|                    |                  |

---

## ✨ Key Features

### 🔐 User Authentication

- User registration and login.
- Personal account management.
- Profile information updates.

### 🎫 Multiple Booking Services

- Train ticket bookings.
- Bus ticket bookings.
- Flight bookings.
- Movie ticket bookings.
- Hotel reservations.
- Restaurant bookings.

### 💳 Wallet Management

- Add money to the wallet.
- View available wallet balance.
- Manage wallet transactions.
- Track payment history.

### 📋 Booking Management

- View previous and current bookings.
- Manage booking details.
- Cancel existing bookings.
- Process refunds for cancelled bookings.

### 🧾 Ticket Generation

- Generate booking tickets and receipts.
- Download booking confirmations.

---

## 🛠️ Technologies Used

| Technology  | Purpose                         |
| ----------- | ------------------------------- |
| Java        | Core application development    |
| Java Swing  | Graphical User Interface        |
| Java AWT    | UI customization and components |
| MySQL       | Database management             |
| JDBC        | Java-MySQL connectivity         |
| Eclipse IDE | Development environment         |

---

## 🗄️ Database Structure

Reservia uses **MySQL** to store and manage application data.

The database is named:

`universal_booking`

The main database tables include:

| Table         | Description                                      |
| ------------- | ------------------------------------------------ |
| `users`       | Stores user registration and account information |
| `wallet`      | Maintains user wallet balances                   |
| `transaction` | Stores wallet transaction records                |
| `bookings`    | Stores booking details and status                |

JDBC is used to establish communication between the Java application and the MySQL database.

---

## ⚙️ Installation & Setup

Follow these steps to run Reservia on your local machine.

### Prerequisites

Make sure you have:

- Java Development Kit (JDK).
- MySQL Server.
- MySQL JDBC Connector.
- Eclipse IDE (recommended).

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR-USERNAME/Reservia-Universal-Booking-System.git
```

### 2. Set Up the Database

1. Open MySQL Workbench or your preferred MySQL client.
2. Locate the SQL scripts inside the `database` folder.
3. Execute the database creation and table scripts.
4. Make sure the `universal_booking` database is created successfully.

### 3. Configure Database Connectivity

Locate the database connection class in the Java source code.

Update the database connection details according to your local MySQL configuration.

Make sure the JDBC driver is included in the project dependencies.

**Note:** Do not commit personal database credentials or passwords to GitHub.

### 4. Import the Project

1. Open Eclipse IDE.
2. Import the downloaded project.
3. Configure the required Java and JDBC dependencies.
4. Verify the MySQL database connection.

### 5. Run the Application

Locate the main class:

`main.java`

Run the application to launch Reservia.

---

## 📂 Project Structure

```text
Reservia-Universal-Booking-System/
│
├── Java CIA Reservia/
│       ├── main.java
│       ├── home.java
│       ├── Wallet.java
│       ├── train.java
│       ├── bus.java
│       ├── flight.java
│       ├── movie.java
│       ├── hotel.java
│       ├── restaurant.java
│       ├── profile.java
│       ├── ticket.java
│       └── ...
│
├── Database/
│   └── SQL scripts
│
├── Reservia_Report.pdf
│
├── Screenshots/
│   └── Application screenshots
│
├── README.md
└── LICENSE
```

---

## 🚀 Download Application

Want to try Reservia without importing the source code?

Download the application executable from the repository's **Releases** section.

**Latest Release:** Reservia v1.0

[View Releases](../../releases)

Refer to the installation instructions and database requirements before running the application.

---

## 🔮 Future Enhancements

Some planned improvements for future versions include:

- Integration of online payment gateways.
- Email and SMS booking notifications.
- Additional booking categories and services.
- Offers, discounts, and loyalty rewards.
- Expansion into a web or mobile application.

---

## 👥 Project Contributors

Reservia was developed as a collaborative group project.

**Team Members:**

- [Joel Daniel]
- [Yashu Gupta]
- [Krisha Mohile]

---

## 🎓 Learning Outcomes

Through this project, we gained practical experience in:

- Java programming and object-oriented concepts.
- GUI development using Java Swing and AWT.
- Database design and MySQL integration.
- JDBC connectivity and SQL operations.
- Building a multi-module desktop application.
- Collaborative software development.

---

## ⚠️ Disclaimer

Reservia is an academic project developed for educational purposes. It demonstrates booking management, database connectivity, and desktop application development. It is not a production-ready commercial booking platform.

---

## 📄 License

This project is licensed under the MIT License.

---

\<p align="center">
&#x20; \<strong>Reservia – Making Every Booking Simpler.\</strong>
\</p>
