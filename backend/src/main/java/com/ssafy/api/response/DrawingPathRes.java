package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;


@Data
@EqualsAndHashCode(callSuper=false)
public class DrawingPathRes extends BaseResponseBody {

    HashMap<Long, String> drawingPath = new HashMap<>();

    public DrawingPathRes(){super();}

    public static DrawingPathRes of(Integer statusCode, String message, HashMap<Long, String> drawingPath){

        DrawingPathRes drawingPathRes = new DrawingPathRes();
        drawingPathRes.setStatusCode(statusCode);
        drawingPathRes.setMessage(message);
        drawingPathRes.setDrawingPath(drawingPath);

        return drawingPathRes;
    }
}
