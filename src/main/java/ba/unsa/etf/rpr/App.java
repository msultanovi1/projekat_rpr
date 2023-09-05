package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.GenreManager;
import ba.unsa.etf.rpr.business.StatusManager;
import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Genre;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import org.apache.commons.cli.*;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class App
{
    /*
    private static final org.apache.commons.cli.Option addUser = new org.apache.commons.cli.Option("addUser", true, "Add a new user to the database (1 argument - name (String))");
    private static final org.apache.commons.cli.Option deleteUser = new org.apache.commons.cli.Option("deleteUser", true, "Delete an existing user from the database (1 argument - id (int))");
    private static final org.apache.commons.cli.Option getAllUsers = new org.apache.commons.cli.Option("getAllUsers", false, "Get all users from the database (0 arguments)");
    private static final org.apache.commons.cli.Option addBook = new org.apache.commons.cli.Option("addBook", true, "Add a new book to the database (5 arguments - look them up with showBookArguments)");
    private static final org.apache.commons.cli.Option deleteBook = new org.apache.commons.cli.Option("deleteBook", true, "Delete an existing book from the database (1 argument - id (int))");
    private static final org.apache.commons.cli.Option getAllBooks = new org.apache.commons.cli.Option("getAllBooks", false, "Get all books from the database (0 arguments)");
    private static final org.apache.commons.cli.Option showBookArguments = new org.apache.commons.cli.Option("showBookArguments", false, "Shows all arguments needed to enter to create a new book (0 arguments)");
    private static final org.apache.commons.cli.Option showVerdicts = new org.apache.commons.cli.Option("showVerdicts", false, "Shows all valid verdicts (0 arguments)");
    private static final UserManager userManager = UserManager.getInstance();
    private static final BookManager bookManager = BookManager.getInstance();

    private static Options loadOptions() {
        Options options = new Options();
        options.addOption(addUser);
        options.addOption(deleteUser);
        options.addOption(getAllUsers);
        options.addOption(addBook);
        options.addOption(deleteBook);
        options.addOption(getAllBooks);
        options.addOption(showBookArguments);
        return options;
    }
    private static void printFormattedOptions(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar projekat_rpr-cli-jar-with-dependencies.jar -option ditto arguments");
        helpFormatter.printOptions(printWriter, 150, options, 2, 7);
        printWriter.close();
    }

    public static void main(String[] args ){

        try {
            Options options = loadOptions();
            CommandLineParser commandLineParser = new DefaultParser();
            CommandLine commandLine = commandLineParser.parse(options, args);

            //System.out.println(commandLine.getArgList().get(1));
            //System.out.println(args);

            if (commandLine.hasOption(showDepartments.getOpt()) || commandLine.hasOption(showDepartments.getLongOpt())) {
                System.out.println("Hera iam");
                DepartmentManager departmentManager = new DepartmentManager();
                System.out.println(departmentManager.getAll());
            } else if (commandLine.hasOption(addDepartment.getOpt()) || commandLine.hasOption(addDepartment.getLongOpt())) {
                DepartmentManager departmentManager = new DepartmentManager();
                departmentManager.add(new Department(1, commandLine.getArgList().get(0)));
            } else if (commandLine.hasOption(deleteDepartment.getOpt()) || commandLine.hasOption(deleteDepartment.getLongOpt())) {
                DepartmentManager departmentManager = new DepartmentManager();
                departmentManager.delete(Integer.parseInt(commandLine.getArgList().get(0)));
            } else if (commandLine.hasOption(showDoctors.getOpt()) || commandLine.hasOption(showDoctors.getLongOpt())) {
                DoctorManager doctorManager = new DoctorManager();
                System.out.println(doctorManager.getAll());
            } else if (commandLine.hasOption(addDoctor.getOpt()) || commandLine.hasOption(addDoctor.getLongOpt())) {
                String name , pass, depName;
                if(commandLine.getArgList().size() == 4){
                    name = commandLine.getArgList().get(0) + " " + commandLine.getArgList().get(1);
                    pass = commandLine.getArgList().get(2);
                    depName = commandLine.getArgList().get(3);
                } else {
                    name = commandLine.getArgList().get(0);
                    pass = commandLine.getArgList().get(1);
                    depName = commandLine.getArgList().get(2);
                }
                DoctorManager doctorManager = new DoctorManager();
                DepartmentManager departmentManager = new DepartmentManager();
                List<Department> department = departmentManager.getByName(depName);
                if (department.size() == 0) {
                    System.out.println("Department with name " + depName + " does not exists");
                } else if (department.size() != 0) {
                    doctorManager.add(new Doctor(1, name, pass, department.get(0)));
                    System.out.println("You've successfully added a new Doctor");
                }
            } else if (commandLine.hasOption(deleteDoctor.getOpt()) || commandLine.hasOption(deleteDoctor.getLongOpt())) {
                DoctorManager doctorManager = new DoctorManager();
                doctorManager.delete(Integer.parseInt(commandLine.getArgList().get(0)));
            } else if (commandLine.hasOption(showPatients.getOpt()) || commandLine.hasOption(showPatients.getLongOpt())) {
                PatientManager patientManager = new PatientManager();
                System.out.println(patientManager.getAll());
            } else if (commandLine.hasOption(addPatient.getOpt()) || commandLine.hasOption(addPatient.getLongOpt())) {
                String name, pass;
                Long UIN;
                int id;
                if(commandLine.getArgList().size() == 5){
                    name = commandLine.getArgList().get(0) + " " + commandLine.getArgList().get(1);
                    pass = commandLine.getArgList().get(2);
                    UIN = Long.parseLong(commandLine.getArgList().get(3));
                    id = Integer.parseInt(commandLine.getArgList().get(4));
                } else {
                    name = commandLine.getArgList().get(0) + " " + commandLine.getArgList().get(1);
                    pass = commandLine.getArgList().get(2);
                    UIN = Long.parseLong(commandLine.getArgList().get(3));
                    id = Integer.parseInt(commandLine.getArgList().get(4));
                }
                PatientManager patientManager = new PatientManager();
                DoctorManager doctorManager = new DoctorManager();
                Doctor doctor = doctorManager.getById(id);
                if (doctor == null) {
                    System.out.println("Doctor does not exists");
                } else if (doctor != null) {
                    patientManager.add(new Patient(1, name, pass,
                            UIN, doctor));
                    System.out.println("You've successfully added a new Patient");
                }
            } else if (commandLine.hasOption(deletePatient.getOpt()) || commandLine.hasOption(deletePatient.getLongOpt())) {
                System.out.println(Integer.parseInt(commandLine.getArgList().get(0)));
                PatientManager patientManager = new PatientManager();
                System.out.println(patientManager.getById(Integer.parseInt(commandLine.getArgList().get(0))));
                patientManager.delete(Integer.parseInt(commandLine.getArgList().get(0)));
            } else if (commandLine.hasOption(showHistories.getOpt()) || commandLine.hasOption(showHistories.getLongOpt())) {
                DiagnosisManager diagnosisManager = new DiagnosisManager();
                System.out.println(diagnosisManager.getAll());
            } else if (commandLine.hasOption(addHistory.getOpt()) || commandLine.hasOption(addHistory.getLongOpt())) {
                PatientManager patientManager = new PatientManager();
                DoctorManager doctorManager = new DoctorManager();
                DiagnosisManager diagnosisManager = new DiagnosisManager();
                Doctor doctor = doctorManager.getById(Integer.parseInt(commandLine.getArgList().get(1)));
                Patient patient = patientManager.getById(Integer.parseInt(commandLine.getArgList().get(0)));
                if (doctor == null || patient == null) {
                    System.out.println("Doctor or patient does not exists");
                } else if (doctor != null || patient != null) {
                    diagnosisManager.add(new History(1, patient, doctor, commandLine.getArgList().get(2)));
                    System.out.println("You've successfully added a new History");
                }
            } else if (commandLine.hasOption(deleteHistory.getOpt()) || commandLine.hasOption(deleteHistory.getLongOpt())) {
                DiagnosisManager diagnosisManager = new DiagnosisManager();
                diagnosisManager.delete(Integer.parseInt(commandLine.getArgList().get(0)));
            } else {
                printFormattedOptions(options);
            }
        } catch (ParseException pe) {
            System.out.println("Problems while entering data");
        } catch (Exception pe) {
            System.out.println(pe.getMessage());
        }
    }*/
}
