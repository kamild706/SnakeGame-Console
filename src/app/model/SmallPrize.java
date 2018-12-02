package app.model;

public class SmallPrize extends Prize {

    public SmallPrize() {
        super(1);
        setExtraLife(false);
    }

    @Override
    protected Object clone() {
        return new SmallPrize();
    }
}
