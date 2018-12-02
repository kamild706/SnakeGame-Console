package app.model;

public class MediumPrize extends Prize {

    public MediumPrize() {
        super(4);
        setExtraLife(false);
    }

    @Override
    protected Object clone() {
        return new MediumPrize();
    }
}
