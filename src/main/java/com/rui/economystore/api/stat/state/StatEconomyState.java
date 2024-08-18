package com.rui.economystore.api.stat.state;

import com.rui.economystore.api.stat.Stat;

import java.util.Objects;

public class StatEconomyState {
    public Stat stat;
    public float money;

    public StatEconomyState(Stat stat, float money) {
        this.stat = stat;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatEconomyState statState = (StatEconomyState) o;
        return money == statState.money &&
                Objects.equals(stat, statState.stat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stat, money);
    }
}
