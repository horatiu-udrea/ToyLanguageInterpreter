package model.types;

import model.values.BoolValue;
import model.values.Value;

public class BoolType implements Type
{
    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof BoolType;
    }

    @Override
    public String toString()
    {
        return "bool";
    }

    @Override
    public BoolValue defaultValue()
    {
        return new BoolValue();
    }

    @Override
    public Type deepCopy()
    {
        return new BoolType();
    }
}
