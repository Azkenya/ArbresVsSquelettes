package model.config;
public class Money {
    private int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public void add(Money m) {
        this.amount += m.amount;
    }

    public boolean remove(Money m) {
        if (this.EnoughMoney(m)) {
            this.amount -= m.amount;
            return true;
        }
        return false;
    }

    public boolean EnoughMoney(Money m) {
        if (this.amount >= m.amount) {
            return true;
        }
        return false;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString() {
        return this.amount + " golds";
    }
}
