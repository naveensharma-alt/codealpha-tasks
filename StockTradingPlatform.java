import java.util.*;

class Stock {
    String symbol;
    double price;

    Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance;

    Portfolio(double balance) {
        this.balance = balance;
    }

    void buyStock(Stock stock, int qty) {
        double cost = stock.price * qty;
        if (balance >= cost) {
            holdings.put(stock.symbol, holdings.getOrDefault(stock.symbol, 0) + qty);
            balance -= cost;
            System.out.println("Bought " + qty + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough balance!");
        }
    }

    void sellStock(Stock stock, int qty) {
        int owned = holdings.getOrDefault(stock.symbol, 0);
        if (owned >= qty) {
            holdings.put(stock.symbol, owned - qty);
            balance += stock.price * qty;
            System.out.println("Sold " + qty + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares to sell!");
        }
    }

    void showPortfolio(Map<String, Stock> market) {
        System.out.println("\n=== Portfolio Summary ===");
        double totalValue = balance;
        for (var entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double price = market.get(symbol).price;
            double value = qty * price;
            System.out.println(symbol + " : " + qty + " shares (Value: " + value + ")");
            totalValue += value;
        }
        System.out.println("Cash Balance: " + balance);
        System.out.println("Total Portfolio Value: " + totalValue + "\n");
    }
}

public class StockTradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Market simulation
        Map<String, Stock> market = new HashMap<>();
        market.put("AAPL", new Stock("AAPL", 150));
        market.put("GOOG", new Stock("GOOG", 2800));
        market.put("TSLA", new Stock("TSLA", 750));

        Portfolio portfolio = new Portfolio(5000);

        while (true) {
            System.out.println("=== Stock Trading Platform ===");
            System.out.println("1. Show Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. Show Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.println("\nMarket Data:");
                for (Stock s : market.values()) {
                    System.out.println(s.symbol + " : $" + s.price);
                }
            } else if (choice == 2) {
                System.out.print("Enter stock symbol: ");
                String sym = sc.next().toUpperCase();
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();
                if (market.containsKey(sym)) {
                    portfolio.buyStock(market.get(sym), qty);
                } else {
                    System.out.println("Invalid stock symbol!");
                }
            } else if (choice == 3) {
                System.out.print("Enter stock symbol: ");
                String sym = sc.next().toUpperCase();
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();
                if (market.containsKey(sym)) {
                    portfolio.sellStock(market.get(sym), qty);
                } else {
                    System.out.println("Invalid stock symbol!");
                }
            } else if (choice == 4) {
                portfolio.showPortfolio(market);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }
        sc.close();
    }
}
