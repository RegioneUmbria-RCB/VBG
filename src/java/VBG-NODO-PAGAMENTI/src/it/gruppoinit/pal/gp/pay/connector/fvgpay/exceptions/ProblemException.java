package it.gruppoinit.pal.gp.pay.connector.fvgpay.exceptions;

import it.gruppoinit.pal.gp.pay.connector.fvgpay.rest.client.model.Problem;

public class ProblemException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -476398954939496279L;
    private Problem problem;

    public Problem getProblem() {

	return problem;
    }

    public ProblemException(Problem problem) {

	super(problem.getDetail());
	this.problem = problem;
    }

    public ProblemException(String message) {

	super(message);
	this.problem = new Problem();
	this.problem.setDetail(message);
	this.problem.setStatus(500);
	this.problem.setTitle(message);
    }

    public ProblemException(Exception e1) {

	super(e1);
	this.problem = new Problem();
	this.problem.setDetail(e1.getMessage());
	this.problem.setStatus(500);
	this.problem.setTitle(e1.getMessage());
    }

    public ProblemException(String message, Exception e) {

	super(e);
	this.problem = new Problem();
	this.problem.setDetail(e.getMessage());
	this.problem.setStatus(500);
	this.problem.setTitle(message);
    }
}
