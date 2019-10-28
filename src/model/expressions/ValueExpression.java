package model.expressions;

import model.programstate.IApplicationDictionary;
import model.values.Value;

public class ValueExpression implements Expression
{
    private Value value;

    public ValueExpression(Value value)
    {
        this.value = value;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable)
    {
        return value;
    }

    @Override
    public Expression deepCopy()
    {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}
