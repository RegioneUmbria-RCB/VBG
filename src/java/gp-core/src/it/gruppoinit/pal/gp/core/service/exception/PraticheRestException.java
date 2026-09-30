package it.gruppoinit.pal.gp.core.service.exception;

import it.gruppoinit.pal.gp.core.service.helper.ProblemResult;

public class PraticheRestException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 6405782581232297758L;
    private ProblemResult problem;

    public PraticheRestException() {

    }

    public PraticheRestException(ProblemResult problem) {

	super(problem.getDetail());
	this.setProblem(problem);
    }

    public PraticheRestException(ProblemResult problem, Throwable throwable) {

	super(problem.getDetail(), throwable);
	this.setProblem(problem);
    }

    public ProblemResult getProblem() {

	return problem;
    }

    public void setProblem(ProblemResult problem) {

	this.problem = problem;
    }
}
