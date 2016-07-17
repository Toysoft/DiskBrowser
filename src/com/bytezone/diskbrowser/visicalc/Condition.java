package com.bytezone.diskbrowser.visicalc;

// Predicate
class Condition
{
  private static final String[] comparators = { "<>", "<=", ">=", "=", "<", ">" };

  private final Sheet parent;

  private String comparator;
  private String conditionText;
  private String valueText;

  private Expression conditionExpression;
  private Expression valueExpression;

  public Condition (Sheet parent, String text)
  {
    this.parent = parent;

    for (String comp : comparators)
    {
      int pos = text.indexOf (comp);
      if (pos > 0)
      {
        conditionText = text.substring (0, pos);
        valueText = text.substring (pos + comp.length ());
        comparator = comp;
        break;
      }
    }

    if (comparator == null)
    {
      if (text.startsWith ("@"))
      {
        conditionText = text;
        valueText = "1";
        comparator = "=";
      }
      else
        System.out.println ("No comparator and not a function");
    }
  }

  public boolean getResult ()
  {
    //    System.out.println (conditionText);
    if (conditionExpression == null)
    {
      //      System.out.printf ("creating %s %s %s%n", conditionText, comparator, valueText);
      conditionExpression = new Expression (parent, conditionText);
      //      System.out.printf ("creating %s%n", valueText);
      valueExpression = new Expression (parent, valueText);

      //      System.out.printf ("calculating %s%n", conditionExpression);
      conditionExpression.calculate ();
      //      System.out.printf ("calculating %s%n", valueExpression);
      valueExpression.calculate ();
    }
    //    System.out.println ("after calculation");

    if (conditionExpression.isError () || valueExpression.isError ())
      return false;

    double conditionResult = conditionExpression.getValue ();
    double valueResult = valueExpression.getValue ();

    if (comparator.equals ("="))
      return conditionResult == valueResult;
    else if (comparator.equals ("<>"))
      return conditionResult != valueResult;
    else if (comparator.equals ("<"))
      return conditionResult < valueResult;
    else if (comparator.equals (">"))
      return conditionResult > valueResult;
    else if (comparator.equals ("<="))
      return conditionResult <= valueResult;
    else if (comparator.equals (">="))
      return conditionResult >= valueResult;
    else
      System.out.printf ("Unexpected comparator result [%s]%n", comparator);

    return false;               // flag error?
  }

  @Override
  public String toString ()
  {
    return String.format ("[cond=%s, op=%s, value=%s]", conditionText, comparator,
                          valueText);
  }
}