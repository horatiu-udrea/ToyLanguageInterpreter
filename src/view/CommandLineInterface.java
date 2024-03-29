package view;

import controller.Controller;
import model.programstate.ProgramState;
import model.statements.Statement;
import repository.IRepository;
import repository.Repository;
import utils.ProgramUtils;
import view.cli.ExitCommand;
import view.cli.RunExampleCommand;
import view.cli.TextMenu;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static utils.ProgramUtils.cleanLogDirectory;

public class CommandLineInterface
{
    public static void main(String[] args)
    {
        cleanLogDirectory();
        List<Statement> statements = ProgramUtils.generatePrograms();
        List<Controller> controllers = IntStream.range(0, statements.size()).mapToObj((index) ->
        {
            ProgramState programState = new ProgramState(statements.get(index));
            IRepository repository = new Repository(programState, String.format("logs/program%d.log", index + 1));
            return new Controller(repository);
        }).collect(Collectors.toList());

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        for (int index = 0; index < statements.size(); index++)
        {
            RunExampleCommand command = new RunExampleCommand(
                    Integer.toString(index + 1),
                    statements.get(index).toString(),
                    controllers.get(index));
            menu.addCommand(command);
        }
        menu.show();
    }
}

