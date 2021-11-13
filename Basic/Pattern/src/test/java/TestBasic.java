import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestBasic {
    public static void parserStrWithRegexOutputGroup1(String patternStr, String source) {
        Pattern pattern = Pattern.compile(patternStr);
        parserStrWithRegexOutputGroup1(pattern, source);
    }

    public static void parserStrWithRegexOutputGroup1(Pattern pattern, String source) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        } else {
            System.out.println("not match");
        }
    }

    @Test
    public void testMatchZeroOrMore() {
        String s = "abcccc";

        /*
         * greedy match: ".*" */
        parserStrWithRegexOutputGroup1("a(.*)c+", s);// output: bccc

        /*
         * reluctant match: ".*?" */
        parserStrWithRegexOutputGroup1("a(.*?)c+", s);// output: b

        /*
         * possessive match: ".*+"*/
        parserStrWithRegexOutputGroup1("a(.*+)c+", s);//output nothing, as not match
    }

    @Test
    public void testMatchOneOrMore() {
        String s = "acccc";

        /*
         * greedy match: ".+" */
        parserStrWithRegexOutputGroup1("a(.+)c+", s);// output: ccc

        /*
         * reluctant match: ".+?" */
        parserStrWithRegexOutputGroup1("a(.+?)c+", s);// output: c

        /*
         * possessive match: ".++"*/
        parserStrWithRegexOutputGroup1("a(.++)c+", s);//output nothing, as not match
    }

    @Test
    public void testMatchOneCharacter() {
        /*
         * [...]*/
        String s1 = "abc";

        parserStrWithRegexOutputGroup1("a([bcd])c", s1);//match one of character b c d, output: b
        parserStrWithRegexOutputGroup1("a([efg])c", s1);//match one of character e f g, not match
    }

    @Test
    public void testNotMatchOneCharacter() {
        /*
         * [^...]*/
        String s1 = "abc";

        parserStrWithRegexOutputGroup1("a([^bcd])c", s1);//match one character other than b c d, not match
        parserStrWithRegexOutputGroup1("a([^efg])c", s1);//match one character other than e f g, output: b

        /*
         * set intersection: [...&&[...]]
         * the charater should match rule in first .. and also the rule in second ...*/
        parserStrWithRegexOutputGroup1("a([A-Za-z&&[^bcd]])c", s1);// match one character in set from a to z other than b c d, output: not match
        parserStrWithRegexOutputGroup1("a([A-Za-z&&[bcd]])c", s1);//output: b
    }

    @Test
    public void testPosixCharacterClasses() {
        /*
         * https://www.oreilly.com/library/view/java-9-regular/9781787288706/51cdf19f-a29f-46cc-93be-2de55a82870d.xhtml*/
        String s = "abc";
        parserStrWithRegexOutputGroup1("a(\\p{Alpha})c", s);// output: b
    }

    @Test
    public void testCaptureNamedGroups() {
        String s = "hello Ala!";
        Matcher matcher = Pattern.compile("hello\\s\\b(?<name>\\p{Alpha}++)\\b.*").matcher(s);
        if (matcher.matches()) {
            System.out.println(matcher.group("name"));
        } else {
            Assert.fail("not match");
        }
    }

    @Test
    public void testNoCaptureNamedGroups() {
        String s = "hello Ala!";
        //capture all group
        Matcher matcher = Pattern.compile("(\\p{Alpha}++)\\s\\b(\\p{Alpha}++)\\b.*").matcher(s);
        if (matcher.matches()) {
            System.out.println(matcher.group(2));// first group is been captured
        } else {
            Assert.fail("not match");
        }

        //only capture second group
        Matcher matcher2 = Pattern.compile("(?:\\p{Alpha}++)\\s\\b(\\p{Alpha}++)\\b.*").matcher(s);
        if (matcher2.matches()) {
            System.out.println(matcher2.group(1));// first group is been ignored
        } else {
            Assert.fail("not match");
        }
    }

    @Test
    public void testFlagComments() {
        /**
         * in this mode, you can provide more white space in regex pattern string to make it more readable, if you want to match whitespace, you need specify the space as "\\ " or "\\s"
         * And also you can add comment after each group of pattern, just need to start with new line if you want to continue add more regex pattern*/
        Pattern commentFlagPattern = Pattern.compile("Hello    #match hello\n\\s(.*)", Pattern.COMMENTS);
        Matcher matcher = commentFlagPattern.matcher("Hello AlaAna");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        } else {
            Assert.fail("not match");
        }

//        you can also enable it with embedded flag expression (?x)
        commentFlagPattern = Pattern.compile("(?x)Hello    #match hello\n\\s(.*)");
        matcher = commentFlagPattern.matcher("Hello AlaAna");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        } else {
            Assert.fail("not match");
        }

//        you can close the flag with (?-x)
        commentFlagPattern = Pattern.compile("(?-x)Hello    #match hello\n\\s(.*)", Pattern.COMMENTS);
        matcher = commentFlagPattern.matcher("Hello AlaAna");// not match, as comment is been closed
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        } else {
            System.out.println("not match");
        }
    }

    @Test
    public void testFlagDotall() {
        /**
         * dotall mode  make "." to support match any character include a line terminator*/
//        Pattern dotallFlagPattern = Pattern.compile("Hello(.*)",Pattern.DOTALL);
        Pattern dotallFlagPattern = Pattern.compile("(?s)Hello(.*)");
        Matcher matcher = dotallFlagPattern.matcher("Hello Ala\nAna");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));
        } else {
            Assert.fail("not match");
        }
    }

    @Test
    public void testFlagLiteral(){
        /**
         * treat the regex pattern as literal string, */
        Pattern literalFlagPattern=Pattern.compile("(?s)Hell0\\d+",Pattern.LITERAL);
        Matcher matcher= literalFlagPattern.matcher("(?s)Hell0\\d+");
        if (matcher.matches()) {
            System.out.println("Matched");
        } else {
            Assert.fail("not match");
        }
    }
    @Test
    public void testFlagMultiline(){
        /**
         * in this mode, "^" and "$" will match just after or just before a line terminator or the end of the input sequence
         * */
        Pattern multilinePattern=Pattern.compile("^(.*)$\\R?(.*)",Pattern.MULTILINE);
        Matcher matcher=multilinePattern.matcher("hello\nworld!");
        if (matcher.matches()) {
            System.out.println(matcher.group(1));//output: hello
        } else {
            Assert.fail("not match");
        }
        System.out.println(Pattern.quote("1\\d+3"));
        System.out.println(multilinePattern.toString());
    }
    @Test
    public void testQuote(){

        Pattern multilinePattern=Pattern.compile("\\Q1\\d+3\\E",Pattern.MULTILINE);
        Matcher matcher=multilinePattern.matcher("1\\d+3");
        if (matcher.matches()) {
            System.out.println("matched");//output: hello
        } else {
            Assert.fail("not match");
        }
    }
}
