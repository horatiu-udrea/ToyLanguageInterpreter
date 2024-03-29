package model.statements;

import exceptions.ConditionNotBoolean;
import exceptions.ProgramException;
import exceptions.TypeMismatchException;
import model.expressions.Expression;
import model.programstate.IApplicationDictionary;
import model.programstate.IApplicationHeap;
import model.programstate.IApplicationStack;
import model.programstate.ProgramState;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements Statement
{
    private Expression condition;
    private Statement thenStatement;
    private Statement elseStatement;

    public IfStatement(
            Expression condition,
            Statement thenStatement,
            Statement elseStatement
    )
    {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws ProgramException
    {
        IApplicationDictionary<String, Value> symbolTable = programState.getSymbolTable();
        IApplicationStack<Statement> executionStack = programState.getExecutionStack();
        IApplicationHeap<Value> heap = programState.getHeap();

        Value conditionValue = condition.evaluate(symbolTable, heap);
        if (!conditionValue.getType().equals(new BoolType()))
            throw new ConditionNotBoolean(condition);

        if (((BoolValue) conditionValue).getValue())
        {
            executionStack.push(thenStatement);
        }
        else
        {
            executionStack.push(elseStatement);
        }
        return null;
    }

    @Override
    public Statement deepCopy()
    {
        return new IfStatement(condition.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public IApplicationDictionary<String, Type> typeCheck(IApplicationDictionary<String, Type> typeEnvironment) throws TypeMismatchException
    {
        Type conditionType = condition.typeCheck(typeEnvironment);
        if (!conditionType.equals(new BoolType()))
        {
            throw new TypeMismatchException(condition, new BoolType(), conditionType);
        }
        thenStatement.typeCheck(typeEnvironment.shallowCopy());
        elseStatement.typeCheck(typeEnvironment.shallowCopy());
        return typeEnvironment;
    }

    @Override
    public String toString()
    {
        return "if " + condition.toString() + " then " + thenStatement.toString() + " else " + elseStatement
                .toString();
    }
}
