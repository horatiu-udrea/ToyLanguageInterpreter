package model.expressions;

import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.types.Type;
import model.values.Value;

public class VariableExpression implements Expression
{
    private String id;

    public VariableExpression(String id)
    {
        this.id = id;
    }

    @Override
    public Value evaluate(IApplicationDictionary<String, Value> symbolTable, IApplicationHeap<Value> heap) throws VariableNotDeclaredException
    {
        Value lookup = symbolTable.lookup(id);
        if (lookup == null)
            throw new VariableNotDeclaredException(id);
        return lookup;
    }

    @Override
    public Expression deepCopy()
    {
        return new VariableExpression(id);
    }

    @Override
    public Type typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        return typeEnvironment.lookup(id);
    }

    @Override
    public String toString()
    {
        return id;
    }
}
