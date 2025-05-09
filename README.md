# Financial Tracker

A Java-based financial management application that allows users to track their income, expenses, and savings across different payment methods including bank transfers, credit cards, and cash.

## Features

- **Multiple Payment Methods**
  - Bank Transfer (with tax calculation for large transactions)
  - Credit Card (with cashback rewards for large purchases)
  - Cash

- **Transaction Management**
  - Add money to accounts
  - Deduct money from accounts
  - Save money
  - Track transaction history

- **Financial Reporting**
  - View current balances
  - Generate monthly reports
  - Export transactions to CSV

- **User Interface**
  - Command-line interface (CLI) version
  - Graphical User Interface (GUI) version with Swing

## Payment Method Features

### Bank Transfer
- Tax calculation for transactions over 100,000
- 5% tax rate on large transactions
- Tracks total tax paid

### Credit Card
- Cashback rewards for purchases over 50,000
- 3% cashback on eligible transactions
- Tracks total cashback earned

### Cash
- Simple cash management
- Basic balance tracking


## Usage Guide

### Initial Setup
1. Launch the application
2. Enter your username
3. Enter your email address
4. The system will automatically create default payment methods (Bank Transfer and Cash)

### Adding Money
1. Select "Add Money" option
2. Choose payment method
3. Enter the amount
4. Confirm transaction

### Deducting Money
1. Select "Deduct Money" option
2. Choose payment method
3. Enter the amount
4. Confirm transaction

### Saving Money
1. Select "Save Money" option
2. Choose payment method
3. Enter the amount to save
4. Confirm transaction

### Adding Credit Card
1. Select "Add a new credit card" option
2. Enter bank name
3. Enter credit card ID
4. Confirm addition

### Viewing Reports
1. Select "Print Current Balances" to view current account status
2. Select "Generate Monthly Report" for detailed monthly financial report
3. Select "Export Transactions to CSV" to save transaction history

## Project Structure

```
src/
├── FinancialTracker/
│   ├── FinancialTrackerDemo.java    # CLI version
│   ├── FinancialTrackerGUI.java     # GUI version
│   └── Main.java                    # Main entry point
├── models/
│   ├── PaymentType.java             # Abstract payment class
│   ├── BankTransfer.java            # Bank transfer implementation
│   ├── CreditCard.java              # Credit card implementation
│   ├── Cash.java                    # Cash implementation
│   ├── Transaction.java             # Transaction handling
│   └── User.java                    # User management
├── exceptions/
│   ├── FinancialTrackerException.java
│   ├── InsufficientFundsException.java
│   ├── ValidationException.java
│   └── CSVExportException.java
├── services/
│   └── ReportingService.java        # Financial reporting
└── utils/
    └── CSVUtil.java                 # CSV export functionality
```

## Error Handling

The application includes comprehensive error handling for:
- Invalid email formats
- Insufficient funds
- Invalid transaction amounts
- File export errors
- Input validation

