import os
import pandas as pd
import csv
import argparse
# Define the directory containing Parquet files



# Function to clean and properly format the string fields
def clean_quotes(value):
    if isinstance(value, str):
        # Remove extra quotes and strip leading/trailing spaces
        value = value.strip().replace('""', '"').replace('"', '')
        # Ensure proper quoting for fields with commas or quotes
        if ',' in value or '"' in value:
            value = f'"{value}"'
    return value

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('output_dir', type=str, help='输出文件夹')
    args = parser.parse_args()
    output_dir = args.output_dir

    parquet_dir = os.path.join(output_dir, 'artifacts')
    csv_dir = os.path.join(output_dir, 'csv')
    # Convert all Parquet files to CSV
    for file_name in os.listdir(parquet_dir):
        if file_name.endswith('.parquet'):
            parquet_file = os.path.join(parquet_dir, file_name)
            csv_file = os.path.join(csv_dir, file_name.replace('.parquet', '.csv'))

            # Load the Parquet file
            df = pd.read_parquet(parquet_file)

            # Clean quotes in string fields
            for column in df.select_dtypes(include=['object']).columns:
                df[column] = df[column].apply(clean_quotes)

            # Save to CSV
            df.to_csv(csv_file, index=False, quoting=csv.QUOTE_NONNUMERIC)
            print(f"Converted {parquet_file} to {csv_file} successfully.")

    print("All Parquet files have been converted to CSV.")
