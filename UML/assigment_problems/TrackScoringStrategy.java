import java.util.*;

interface TrackScoringStrategy {
    String getTrackName();
    double calculateFinalScore(int idea, int execution, int presentation);
}

class InnovationTrackStrategy implements TrackScoringStrategy {
    @Override
    public String getTrackName() {
        return "Innovation";
    }

    @Override
    public double calculateFinalScore(int idea, int execution, int presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenTrackStrategy implements TrackScoringStrategy {
    @Override
    public String getTrackName() {
        return "Open";
    }

    @Override
    public double calculateFinalScore(int idea, int execution, int presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private final String studentId;
    private final String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }
}

class Score {
    private int idea;
    private int execution;
    private int presentation;

    public Score(int idea, int execution, int presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public int getIdea() { return idea; }
    public int getExecution() { return execution; }
    public int getPresentation() { return presentation; }

    public void setScores(int idea, int execution, int presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }
}

class Project {
    private final String title;
    private final Team team;
    private Score score;

    public Project(String title, Team team) {
        this.title = title;
        this.team = team;
    }

    public String getTitle() { return title; }
    public Team getTeam() { return team; }
    public Score getScore() { return score; }

    public void setScore(Score score) {
        this.score = score;
    }

    public double getFinalScore() {
        if (score == null) return 0.0;
        return team.getTrack().calculateFinalScore(
            score.getIdea(), score.getExecution(), score.getPresentation()
        );
    }
}

class Team {
    private final String name;
    private final List members;
    private final TrackScoringStrategy track;
    private Project project;

    public Team(String name, List members, TrackScoringStrategy track) {
        this.name = name;
        this.members = members;
        this.track = track;
    }

    public String getName() { return name; }
    public List getMembers() { return members; }
    public TrackScoringStrategy getTrack() { return track; }
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }
}

class Judge {
    private final String judgeId;
    private final String name;

    public Judge(String judgeId, String name) {
        this.judgeId = judgeId;
        this.name = name;
    }

    public void scoreProject(Hackathon hackathon, String projectName, int idea, int execution, int presentation) {
        hackathon.scoreProject(projectName, idea, execution, presentation);
    }
}

enum HackathonState {
    REGISTRATION,
    JUDGING,
    PUBLISHED
}

class Hackathon {
    private final String name;
    private final Map teams = new HashMap<>();
    private final Map projects = new HashMap<>();
    private final Set registeredStudents = new HashSet<>();
    private HackathonState state = HackathonState.REGISTRATION;

    public Hackathon(String name) {
        this.name = name;
    }

    public boolean registerTeam(String teamName, List members, TrackScoringStrategy track) {
        if (state != HackathonState.REGISTRATION) {
            System.out.println("Registration failed: Hackathon registration is closed.");
            return false;
        }

        if (members == null || members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student student : members) {
            if (registeredStudents.contains(student)) {
                System.out.println("Registration failed: Student " + student.getName() + " is already registered in another team.");
                return false;
            }
        }

        Team team = new Team(teamName, members, track);
        teams.put(teamName, team);
        registeredStudents.addAll(members);

        System.out.printf("Team %s registered (%d members, %s track).%n", 
                teamName, members.size(), track.getTrackName());
        return true;
    }

    public boolean submitProject(String teamName, String projectTitle) {
        Team team = teams.get(teamName);
        if (team == null) {
            System.out.println("Submission failed: Team not found.");
            return false;
        }

        if (team.getProject() != null) {
            System.out.println("Submission failed: Team has already submitted a project.");
            return false;
        }

        Project project = new Project(projectTitle, team);
        team.setProject(project);
        projects.put(projectTitle, project);

        if (state == HackathonState.REGISTRATION) {
            state = HackathonState.JUDGING;
        }

        System.out.printf("Project '%s' submitted by %s.%n", projectTitle, teamName);
        return true;
    }

    public void scoreProject(String projectTitle, int idea, int execution, int presentation) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        Project project = projects.get(projectTitle);
        if (project == null) {
            System.out.println("Scoring failed: Project not found.");
            return;
        }

        Score score = new Score(idea, execution, presentation);
        project.setScore(score);

        System.out.printf("Score recorded for '%s'. Final score: %.2f.%n", 
                projectTitle, project.getFinalScore());
    }

    public void publishResults() {
        this.state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class CodeSprintJudgingDesk {
    public static void main(String[] args) {
        Hackathon codeSprint = new Hackathon("Code Sprint 2026");

        Student asha = new Student("S1", "Asha");
        Student ravi = new Student("S2", "Ravi");
        Student neha = new Student("S3", "Neha");
        Student kiran = new Student("S4", "Kiran");

        TrackScoringStrategy innovationTrack = new InnovationTrackStrategy();
        TrackScoringStrategy openTrack = new OpenTrackStrategy();

        codeSprint.registerTeam("ByteBusters", Arrays.asList(asha, ravi, neha), innovationTrack);
        codeSprint.registerTeam("SoloCoder", Collections.singletonList(kiran), openTrack);
        codeSprint.submitProject("ByteBusters", "SmartAttend");

        Judge judge1 = new Judge("J1", "Dr. Smith");
        judge1.scoreProject(codeSprint, "SmartAttend", 8, 7, 9);

        codeSprint.publishResults();
        judge1.scoreProject(codeSprint, "SmartAttend", 10, 7, 9);
    }
}