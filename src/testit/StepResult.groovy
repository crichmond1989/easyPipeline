package testit

import groovy.transform.CompileStatic

import testit.ResultStatus
import testit.StepCategory

class StepResult implements Serializable {
    StepCategory category
    String message
    String type
    Throwable error
    String trace

    @CompileStatic
    ResultStatus getStatus() {
        switch(category) {
            case StepCategory.Error:
            case StepCategory.StandardError:
                return ResultStatus.Error

            case StepCategory.Failure:
                return ResultStatus.Failure

            case StepCategory.Complete:
            case StepCategory.StandardOutput:
                return ResultStatus.Success
        }
    }

    @CompileStatic
    String[] getStatusLog() {
        final rows = []

        rows += "Result: $status"

        switch(status) {
            case ResultStatus.Error:
                if (type)
                    rows += "Error: $type"
                
                if (this.message)
                    rows += "Message: $message"

                if (this.trace)
                    rows += "Stack: $trace"
                
                break
            
            case ResultStatus.Failure:
            case ResultStatus.Success:
                if (this.message)
                    rows += "Message: $message"
                
                break
        }

        return rows
    }

    @CompileStatic
    static StepResult completed() {
        return new StepResult(
            category: StepCategory.Complete
        )
    }

    @CompileStatic
    static StepResult errored(Throwable error) {
        return new StepResult(
            category: StepCategory.Error,
            message: error.getMessage(),
            type: error.class.getName(),
            error: error,
            trace: error.getStackTrace().join("\n")
        )
    }

    @CompileStatic
    static StepResult failed(AssertionError error) {
        return new StepResult(
            category: StepCategory.Failure,
            message: error.getMessage(),
            type: error.class.getName(),
            error: error,
            trace: error.getStackTrace().join("\n")
        )
    }

    @CompileStatic
    static StepResult wrote(String message) {
        return new StepResult(
            category: StepCategory.StandardOutput,
            message: message
        )
    }

    @CompileStatic
    static StepResult wroteError(String message) {
        return new StepResult(
            category: StepCategory.StandardError,
            message: message
        )
    }
}