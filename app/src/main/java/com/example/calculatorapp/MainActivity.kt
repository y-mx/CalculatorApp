package com.example.calculatorapp

import android.media.VolumeShaper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Button
import kotlin.math.*


private const val Result_Content = "ResultContent"
private const val Operation_Content="OperationContent"
private const val Operand_1="Operand1"
private const val Operand_2="Operand2"
private const val Operator="Operator"
private const val LastDig="LASTDIG"
private const val LastOp="LASTOP"
private const val LastEquals="LASTEQUALS"
private const val NextNum="NEXTNUM"
private const val DoneOp="DONEOP"
private const val Done1st="DONE1ST"
private const val NumOp1="NUMOP1"
private const val NumOp2="NUMOP2"
private const val Ans="ANS"
class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView
    private lateinit var operation: TextView

    private var op1: Double = 0.0
    private var op2: Double = 0.0
    private var ans: Double = 0.0
    private var operator:String = ""

    private var txtnum1: String=""
    private var txtnum2: String=""

    private var lastDig: Boolean=false
    private var lastOp: Boolean=false
    private var lastEquals: Boolean=false
    private var nextnum: Boolean=false
    private var doneop: Boolean=false
    private var done1st:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result=findViewById(R.id.result)
        operation=findViewById(R.id.operation)

    }
    fun onDigit(view: View){
        if(!doneop&&!done1st){
            if(lastEquals){
                txtnum1=""
                txtnum2=""
                operator=""
                operation.text=""
                result.text=""
                nextnum=false
            }
            operation.append((view as Button).text)
            if(!nextnum){
                txtnum1+=(view as Button).text
            }else{
                txtnum2+=(view as Button).text
            }
            lastDig=true
            lastOp=false
            lastEquals=false
        }
    }
    fun onDot(view: View){
        if(!doneop&&lastDig&&!done1st){
            operation.append((view as Button).text)
            if(!nextnum){
                txtnum1+=(view as Button).text
            }else{
                txtnum2+=(view as Button).text
            }
        }
        lastDig=false
        lastOp=false
    }
    fun onOp(view: View){
        if(!doneop&&lastDig&&!nextnum){
            op1 = txtnum1.toDouble()
            operation.append((view as Button).text)
            operator=(view as Button).text.toString()
            lastDig=false
            lastOp=true
            lastEquals=false
            nextnum=true
            done1st=false
        }
    }

    fun onEquals(view:View){
        if(lastDig&&nextnum&&!operator.equals("")){
            op2 = txtnum2.toDouble()
            calculate(op1, op2, operator)
            lastEquals=true
            lastDig=false
            lastOp=false
            done1st=false
        }
    }

    fun onPi(view: View){
        if((lastOp&&!nextnum)){
            op2= PI
            operation.append("pi")
            doneop=true
            lastOp=false
            lastDig=false
        }else if(lastEquals){
            op1 = PI
            operation.append("pi")
            lastDig=true
            lastEquals=false
            nextnum=true
        }
    }

    fun onAns(view: View){
        if((lastOp&&!nextnum)){
            op2= ans
            operation.append(ans.toString())
            doneop=true
            lastOp=false
            lastDig=false
        }else if(lastEquals){
            op1 = ans
            operation.append(ans.toString())
            lastDig=true
            lastEquals=false
            nextnum=true
        }
    }

    fun calculate(operand1:Double, operand2:Double, operator:String){
        var res:Double=0.0
        when(operator){
            "+"->res=operand1+operand2
            "-"->res=operand1-operand2
            "*"->res=operand1*operand2
            "/"->{
                if(operand2!=0.0) res=operand1/operand2
                else result.text="Error"
            }
            getString(R.string.sqrt)->{
                if(operand2>0.0)res=operand1*sqrt(operand2)
            }
            "^"->{
                res=operand1.pow(operand2)
            }
            "abs"->res=operand1*abs(operand2)
            "mod"->res=operand1%operand2
            "cos"->res=operand1* cos(operand2)
            "sin"->res=operand1* sin(operand2)
            "tan"->res=operand1* tan(operand2)
            else->{
                    result.text="Error"
            }
        }
        if(!result.text.toString().equals("Error")){
            result.text=res.toString()
            ans=res
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString(Result_Content, result.text.toString())
        outState?.putString(Operation_Content, operation.text.toString())
        outState?.putString(Operand_1, txtnum1)
        outState?.putString(Operand_2, txtnum2)
        outState?.putDouble(NumOp1, op1)
        outState?.putDouble(NumOp2, op2)
        outState?.putString(Operator, operator)
        outState?.putBoolean(LastDig, lastDig)
        outState?.putBoolean(LastOp, lastOp)
        outState?.putBoolean(LastEquals, lastEquals)
        outState?.putBoolean(NextNum, nextnum)
        outState?.putBoolean(DoneOp, doneop)
        outState?.putBoolean(Done1st, done1st)
        outState?.putDouble(Ans, ans)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        result.text=savedInstanceState?.getString(Result_Content, "")
        operation.text=savedInstanceState?.getString(Operation_Content, "")
        txtnum1=savedInstanceState?.getString(Operand_1, "")
        txtnum2=savedInstanceState?.getString(Operand_2, "")
        op1=savedInstanceState?.getDouble(NumOp1, 0.0)
        op2=savedInstanceState?.getDouble(NumOp2, 0.0)
        operator=savedInstanceState?.getString(Operator, "")
        lastDig=savedInstanceState?.getBoolean(LastDig, false)
        lastOp=savedInstanceState?.getBoolean(LastOp, false)
        lastEquals=savedInstanceState?.getBoolean(LastEquals, false)
        nextnum=savedInstanceState?.getBoolean(NextNum, false)
        doneop=savedInstanceState?.getBoolean(DoneOp, false)
        ans=savedInstanceState?.getDouble(Ans, 0.0)
        //Ethan
    }
}