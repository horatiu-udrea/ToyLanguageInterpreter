package view.cli;

import controller.Controller;

public class RunExampleCommand extends Command
{
    private Controller controller;

    public RunExampleCommand(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute()
    {
        controller.execute();
        System.out.println("Program execution finished");
    }
}

