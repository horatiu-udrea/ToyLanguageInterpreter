package model.statements;

import exceptions.ProgramException;
import exceptions.AssignTypeMismatchException;
import exceptions.TypeMismatchException;
import exceptions.VariableNotDeclaredException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.ProgramState;
import model.types.Type;
import model.values.Value;

public class AssignStatement implements Statement
{
    private String id;
    private Expression expression;

    public AssignStatement(String id, Expression expression)
    {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationHeap<Value> heap = programState.getHeap();
        Value lookup = symbolTable.lookup(id);
        if (lookup == null)
            throw new VariableNotDeclaredException(id);

        Value expressionValue = expression.evaluate(symbolTable, heap);
        if(!lookup.getType().equals(expressionValue.getType()))
            throw new AssignTypeMismatchException(lookup, expressionValue);

        symbolTable.update(id, expressionValue);
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new AssignStatement(id, expression.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type variableType = typeEnvironment.lookup(id);
        Type expressionType = expression.typeCheck(typeEnvironment);
        if (!variableType.equals(expressionType))
        {
            throw new TypeMismatchException(expression, variableType, expressionType);
        }
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return id + " = " + expression;
    }
}
