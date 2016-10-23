import os.path
import collections
import pprint
import csv

def calculate_averages(counter):
    skips = 0
    for skipValue in ["", "None"]:
        skips += counter.pop(skipValue, 0)
    answers = sum(counter.values())
    try:
        average = sum([int(x[0]) * x[1] for x in counter.items()]) / answers
    except ZeroDivisionError:
        average = 0
    return float(answers), float(skips), float(average)


def get_survey_data(filepath):
    results = collections.defaultdict(collections.Counter)
    special_columns = set("country,least_empathic,most_empathic,name,question_missing,timestamp,uuid".split(","))

    with open(filepath) as f:
        reader = csv.DictReader(f)


        for row in reader:
            for k, v in row.items():
                if k not in special_columns:
                    results[k][v] += 1

    result = {}
    for company_name, c in results.items():
        answers, skips, average = calculate_averages(c)
        result[company_name] = (
            {
                "skips":skips,
                "average":average,
                "answers":answers
            }
        )
    return result


def get_default_row():
    return {
        "skips":0.0,
        "average":0.0,
        "answers":0.0
    }

def enhance_results(results, filename):
    enhanced_data = []

    with open(filename) as f:
        reader = csv.DictReader(f)
        for row in reader:
            ehnanced_row = {}
            extra = results.get(row["company"], get_default_row())
            ehnanced_row.update(row)
            ehnanced_row.update(extra)
            enhanced_data.append(ehnanced_row)
    return enhanced_data

def write_result(file_path, data):
    column_names = set()

    for row in data:
        column_names.update(row.keys())

    column_names = list(column_names)

    with open(file_path, "w", newline='') as ff:
        writer = csv.DictWriter(ff, column_names, )
        writer.writerow(dict(zip(column_names, column_names)))
        writer.writerows(data)


    print("Done!")


def main():
    resource_path = os.path.abspath(os.path.join(os.getcwd(), "..", "resources", "inputs"))
    results = get_survey_data(os.path.join(resource_path, "empathy_companies.csv"))
    enhanced_results = enhance_results(
        results = results,
        filename = os.path.join(resource_path, "input_data.csv")
    )

    write_result(
        file_path = os.path.join(resource_path, "enhanced_input_data.csv"),
        data = enhanced_results
    )


if __name__ == "__main__":
    main()