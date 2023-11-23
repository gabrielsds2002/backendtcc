package br.com.pix.tcc.config;

import org.springframework.http.HttpStatus;

public class BaseResponse {


    private Object data;



    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class MetaData{
        private HttpStatus status;
        private String message;


        public MetaData(HttpStatus status, String menssage) {
            super();
            this.status=status;
            this.message=menssage;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static BaseResponse ok(final Object data) {return  of(null,data);}

    public static BaseResponse error(HttpStatus status, String menssage){
        return of(new MetaData(status,menssage),null);
    }

    public static BaseResponse of(final MetaData metaData,final Object data){
        final BaseResponse baseResponse = new BaseResponse();
        baseResponse.data = data;
        return baseResponse;
    }


}
