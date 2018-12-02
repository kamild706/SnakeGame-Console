package app.model;

public class BigPrize extends Prize {

    public BigPrize() {
        super(10);
        setExtraLife(true);
    }

    @Override
    protected Object clone() {
        return new BigPrize();
    }
}
