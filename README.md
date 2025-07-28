<div align="center">

# 🏠 Loan EMI Calculator

A full-stack application to calculate Equated Monthly Installments (EMI) for loans and generate amortization schedules, with PDF export functionality.

</div>

---

## ✨ Features

- Calculate EMI based on principal, interest rate, and tenure.
- Generate a detailed amortization schedule.
- Download the amortization schedule as a PDF.

### 🚀 Missing Features

For a list of planned features and enhancements, please refer to the [`missing_features.txt`](./missing_features.txt) file.

## 🛠️ Technologies Used

### Backend
- **Java 11+**
- **Spring Boot**: Framework for building robust, production-ready applications.
- **Spring Data JPA**: For data persistence and ORM.
- **SQLite**: Lightweight, file-based database for local storage.
- **iTextPDF**: For generating PDF documents.
- **Maven**: Dependency management and build automation.

### Frontend
- **React**: JavaScript library for building user interfaces.
- **Node.js & npm**: JavaScript runtime and package manager.
- **HTML/CSS**: Standard web technologies for structure and styling.

## ⚙️ Setup and Installation

To get the application up and running on your local machine, follow the detailed instructions in the [`build_instructions.txt`](./build_instructions.txt) file.

## 🚀 Usage

Once both the backend and frontend servers are running:

1.  Open your web browser and navigate to `http://localhost:3000`.
2.  Enter the loan details (Principal Amount, Interest Rate, and Loan Tenure).
3.  Click the "Calculate EMI" button to see the EMI and the amortization schedule.
4.  Use the "Download PDF" button to save the amortization schedule.

## 🤝 Contributing

Contributions are welcome! If you have suggestions for improvements or new features, please feel free to open an issue or submit a pull request.

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
