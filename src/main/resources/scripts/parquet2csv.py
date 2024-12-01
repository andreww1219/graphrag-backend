import os
import pandas as pd
import csv
import argparse
import sys

def ensure_dir(directory):
    """确保目录存在，如果不存在则创建"""
    if not os.path.exists(directory):
        try:
            os.makedirs(directory)
            print(f"创建目录: {directory}")
        except Exception as e:
            print(f"创建目录失败 {directory}: {str(e)}")
            sys.exit(1)

def clean_quotes(value):
    """清理和格式化字符串字段"""
    if isinstance(value, str):
        value = value.strip().replace('""', '"').replace('"', '')
        if ',' in value or '"' in value:
            value = f'"{value}"'
    return value

def convert_parquet_to_csv(parquet_dir, csv_dir):
    """将 Parquet 文件转换为 CSV 文件"""
    # 确保两个目录都存在
    ensure_dir(parquet_dir)
    ensure_dir(csv_dir)

    # 检查 parquet 目录中是否有文件
    parquet_files = [f for f in os.listdir(parquet_dir) if f.endswith('.parquet')]
    if not parquet_files:
        print(f"警告: 在 {parquet_dir} 中没有找到 .parquet 文件")
        return

    # 转换所有 Parquet 文件到 CSV
    for file_name in parquet_files:
        parquet_file = os.path.join(parquet_dir, file_name)
        csv_file = os.path.join(csv_dir, file_name.replace('.parquet', '.csv'))

        try:
            # 加载 Parquet 文件
            df = pd.read_parquet(parquet_file)

            # 清理字符串字段中的引号
            for column in df.select_dtypes(include=['object']).columns:
                df[column] = df[column].apply(clean_quotes)

            # 保存为 CSV
            df.to_csv(csv_file, index=False, quoting=csv.QUOTE_NONNUMERIC)
            print(f"成功转换 {parquet_file} 到 {csv_file}")
        except Exception as e:
            print(f"转换文件 {file_name} 时出错: {str(e)}")

def main():
    # 设置命令行参数解析
    parser = argparse.ArgumentParser(description='将 Parquet 文件转换为 CSV 文件')
    parser.add_argument('parquet_dir', help='包含 Parquet 文件的目录路径')
    parser.add_argument('csv_dir', help='CSV 文件的输出目录路径')

    args = parser.parse_args()

    # 执行转换
    convert_parquet_to_csv(args.parquet_dir, args.csv_dir)
    print("所有 Parquet 文件转换完成")

if __name__ == '__main__':
    main()
